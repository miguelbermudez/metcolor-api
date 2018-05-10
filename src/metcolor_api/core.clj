(ns metcolor-api.core
  (:require [system.repl :refer [set-init! start]]
            [metcolor-api.systems :refer [base-system]]))

(defn init []
  (let [system #'base-system]
    (set-init! system)
    (start)))
