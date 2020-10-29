(ns psonia.app.panels.cropper
  (:require ["cropper" :refer (Cropper) :as cropper]))

(defn init-cropper [src img-div]
  (let [img (.createElement js/document "img")
        crop (fn [e] (js/console.log e))
        c (Cropper. img)]
    (.setAttribute img "style" "max-width: 100%; display: block;")
    (.setAttribute img "src" src)
    (.appendChild img-div img)))
