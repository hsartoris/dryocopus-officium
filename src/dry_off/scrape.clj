(ns dry-off.scrape
  (:require [lambdaisland.uri :refer [uri]]
            [clojure.java.io :as io]
            [hickory.core :as h]
            [hickory.select :as s]
            [clj-http.client :as http]))

(defn get-tree [uri]
  (-> uri str http/get :body h/parse h/as-hickory))

(defn parser [selector]
  #(s/select selector %))

(def parsers
  {"www.ripleyconservancy.org" 
   (parser (s/child (s/class :wsite-section-elements)
                   (s/class :paragraph)))

   "www.dec.ny.gov" (parser (s/tag :table))
   "hvatoday.org"   (parser (s/id :genesis-content))

   "catskillcenter.org" 
   (parser (s/and (s/tag :ul)
                  (s/attr :data-rte-list)))

   "neiwpcc.org"
   (fn [html]
     (let [ny-id (-> (s/and (s/class :collapseomatic)
                            (s/find-in-text #"^New York$"))
                     (s/select html)
                     first :attrs :id)]
       (prn ny-id)
       (-> (s/child (s/id (str "target-" ny-id))
                    s/any)
           (s/select html))))

   "clctrust.org" (parser (s/child (s/class :entry-content)
                                   (s/class :container)))
   "www.nycwatershed.org" 
   (fn [html]
     (let [p (s/child (s/and (s/class :content)
                             (s/class :entry)
                             (s/class :fr)
                             (s/attr :role #(= % "main")))
                      (s/tag :div)
                      (s/tag :p))
           parsed (s/select p html)]
       (partition 2 parsed)))

   "dutchessland.org" (parser (s/id :main))
   "www.scenichudson.org"
   (fn [html]
     (let [p (s/child (s/tag :article)
                      (s/class :entry-content)
                      s/any)
           parsed (s/select p html)]
       (drop-while #(not= "Current Openings" (first (:content %)))
                   parsed)))

   "hudsonia.org" 
   (fn [html] 
     (let [p (s/child (s/class :post)
                      (s/class :entry)
                      (s/tag :p))
           parsed (s/select p html)]
       (-> #(= "EMPLOYMENT OPPORTUNITIES"
               (-> % :content first :content first :content first))
           (filter parsed)
           first)))

   "www.riverkeeper.org"
   (parser (s/child (s/and (s/tag :ul)
                           (s/class :accordion))
                    (s/tag :li)))})



(defn parse [{:keys [html host]}]
  (s/select (get parsers host) html))


;(defmulti parse 
;  (fn [{:keys [uri]}] (:host uri)))
;
;(defmethod parse "www.ripleyconservancy.org"
;  [{:keys [html]}]
;  (s/select (s/child (s/class :wsite-section-elements)
;                     (s/class :paragraph))
;            html))
;
;(defmethod parse "www.dec.ny.gov"
;  [{:keys [html]}]
;  ;(s/select (s/title "Positions Open to the General Public")
;  ; (s/select (s/attr   ; TODO
;  (s/select (s/tag :table) html))
;  ;(s/select (s/id :contentContainer) html))

(defn scrape [link]
  (let [host (:host link)
        html (get-tree link)
        parsed ((get parsers host) html)]
    {:uri link
     :host host
     ;:html html
     :parsed parsed}))
