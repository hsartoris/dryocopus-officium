(ns dry-off.notify
  (:require [postal.core :refer [send-message]]
            [postal.support :refer [message-id]]))

(def host
  {:host "localhost"
   :user "user"
   :pass "pass"
   :port 587
   :tls true})

(def ex-msg
  {:from "jobs@sa.rtor.is"
   :to "hsartoris@gmail.com"
   ;:to "hsartoris@localhost"
   :subject "Test"
   :body "Hello world"
   :message-id #(message-id "sa.rtor.is")})
