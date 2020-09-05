(ns dry-off.notify
  (:require [postal.core :refer [send-message]]))

(def ex-msg
  {:from "jobs@sa.rtor.is"
   :to "hsartoris@localhost"
   :subject "Test"
   :body "Hello world"})
