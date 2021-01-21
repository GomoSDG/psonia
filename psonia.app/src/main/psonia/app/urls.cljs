(ns psonia.app.urls
  (:require [reitit.frontend.easy :as rfe]
            [reitit.frontend :as rf]))

(defn resolve-href
  "Resolves the URL for the given key or path."
  [to path-params query-params]
  (if (keyword? to)
    (rfe/href to path-params query-params)
    #_(let [match  (rf/match-by-path router to)
          route  (-> match :data :name)
          params (or path-params (:path-params match))
          query  (or query-params (:query-params match))]
      (if match
        (rfe/href route params query)
        to))))

