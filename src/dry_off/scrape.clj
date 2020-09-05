(ns dry-off.scrape
  (:require [lambdaisland.uri :refer [uri]]
            [clojure.java.io :as io]
            [hickory.core :as h]
            [hickory.select :as s]
            [clj-http.client :as http]))

(defn get-tree [uri]
  (-> uri str http/get :body h/parse h/as-hickory))

(defmulti scrape (fn [uri] (:host uri)))

(defmethod scrape "www.ripleyconservancy.org"
  [uri]
  (s/select (s/child (s/class :wsite-section-elements)
                     (s/class :paragraph))
            (get-tree uri)))

(defmethod scrape "www.dec.ny.gov"
  [uri]
  ;(s/select (s/title "Positions Open to the General Public")
  ; (s/select (s/attr   ; TODO
  (s/select (s/id :contentContainer)
            (get-tree uri)))
