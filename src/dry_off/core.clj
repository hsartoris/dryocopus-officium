(ns dry-off.core
  (:require [lambdaisland.uri :refer [uri]]
            [clojure.java.io :as io]
            [dry-off.notify :as notify])
  (:gen-class))


(def links (->> "links"
                io/resource
                slurp
                clojure.string/split-lines
                (mapv uri)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
