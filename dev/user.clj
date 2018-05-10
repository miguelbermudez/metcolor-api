(ns user
  (:require [system.repl :refer [system set-init! start stop reset]]
            [metcolor-api.systems :refer [base-system]]))

(set-init! #'base-system)
