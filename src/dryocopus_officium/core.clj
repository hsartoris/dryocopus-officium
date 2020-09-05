(ns dryocopus-officium.core
  (:require [lambdaisland.uri :refer [uri]]
            [clojure.java.io :as io])
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
