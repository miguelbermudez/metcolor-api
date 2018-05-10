(ns metcolor-api.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults api-defaults]]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.util.response :refer [response content-type charset]]
            [cheshire.core :refer :all]
            [system.repl :refer [system]]
            [metcolor-api.db :as db]))

(defroutes app-routes
           (GET "/" [] "Hello World! I'm running on Google App Engine.")

           (context "/work/:work-id" [work-id]
             (GET "/" [] (fn [_]
                           (let [db-spec (:db-spec (:db system))]
                             (-> (db/object-by-id db-spec {:object_id work-id})
                                 response
                                 (content-type "application/json")
                                 (charset "UTF-8"))))))

           (route/not-found "Not Found"))

(def app
  (-> (wrap-defaults app-routes api-defaults)
      wrap-json-response))

(def dev-app
  (-> app-routes
      wrap-reload
      (wrap-defaults api-defaults)
      wrap-json-response))

(comment

  (let [db-spec (:db-spec (:db system))
        item  (db/object-by-id db-spec {:object_id "591857"})]
    (encode item)))