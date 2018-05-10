(ns metcolor-api.db
  (:require [hugsql.core :as hugsql]
            [clojure.java.jdbc :as jdbc]
            [cheshire.core :as json]
            [clojure.pprint :as pprint]
            [clojure.string :as string]))

;;
;; https://github.com/nilenso/honeysql-postgres/issues/11
;;

;; Maps

(extend-protocol jdbc/ISQLValue
  clojure.lang.IPersistentMap
  (sql-value [value]
    (doto (org.postgresql.util.PGobject.)
      (.setType "json")
      (.setValue (json/encode value)))))

(extend-protocol jdbc/IResultSetReadColumn
  org.postgresql.util.PGobject
  (result-set-read-column [pgobj metadata idx]
    (let [type (.getType pgobj)
          value (.getValue pgobj)]
      (case type
        "json" (json/decode value true)
        value                                               ; Default
        ))))

;; Vectors
(defn value-to-json-pgobject [value]
  (doto (org.postgresql.util.PGobject.)
    (.setType "json")
    (.setValue (json/decode value))))

(extend-protocol jdbc/ISQLValue
  clojure.lang.IPersistentMap
  (sql-value [value] (value-to-json-pgobject value))

  clojure.lang.IPersistentVector
  (sql-value [value] (value-to-json-pgobject value)))

;; save some typing
(def pp pprint/pprint)

(defn ppsv
  "Pretty print an sqlvec"
  [sv]
  (println
    (string/join ""
                 ["[\""
                  (-> (first sv)
                      (string/replace #"\"" "\\\\\"")
                      (string/replace #"\n" "\n  "))
                  "\""
                  (when (seq (rest sv)) "\n,")
                  (string/replace
                    (string/join ","
                                 (map #(with-out-str (pp %)) (rest sv)))
                    #"\n$"
                    "")
                  "]\n"])))

(hugsql/def-db-fns "metcolor_api/sql/objects.sql")
(hugsql/def-sqlvec-fns "metcolor_api/sql/objects.sql")
