(defproject metcolor-api "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/java.jdbc "0.7.5"]
                 [org.postgresql/postgresql "42.1.4"]
                 [com.google.cloud.sql/postgres-socket-factory "1.0.8"]
                 [org.danielsz/system "0.4.1"]
                 [com.layerware/hugsql "0.4.8"]
                 [environ "1.1.0"]
                 [compojure "1.6.1"]
                 [ring "1.6.3"]
                 [ring/ring-defaults "0.2.1"]
                 [ring/ring-json "0.4.0"]]
  :plugins [[lein-ring "0.12.1" :exclusions [org.clojure/clojure]]]
  :ring {:handler metcolor-api.handler/app
         :init metcolor-api.core/init}
  :profiles {:dev {:source-paths ["dev/"]
                   :dependencies [[javax.servlet/servlet-api "2.5"]
                                  [ring/ring-devel "1.6.3"]
                                  [ring/ring-mock "0.3.0"]]
                   :ring {:handler metcolor-api.handler/dev-app
                          :init metcolor-api.core/init}
                   :auto-reload? true
                   :auto-refresh? true
                   :stacktraces? true}})
