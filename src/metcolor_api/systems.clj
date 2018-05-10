(ns metcolor-api.systems
  (:require
   [environ.core :refer [env]]
   [system.core :refer [defsystem]]
   (system.components
    [postgres :refer [new-postgres-database]])))

(def db-spec
  {:connection-uri (format
                     "jdbc:postgresql://google/%s?useSSL=true&socketFactory=com.google.cloud.sql.postgres.SocketFactory&socketFactoryArg=%s&user=%s&password=%s"
                     (env :database-name)
                     (env :database-instance-name)
                     (env :database-user)
                     (env :database-pass))})

(defsystem my-system
  [:db (new-postgres-database db-spec)])

(def base-system my-system)
