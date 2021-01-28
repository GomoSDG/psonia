(ns psonia.app.panels.product.core
  (:require [psonia.app.panels.components :refer [multi-level-navbar static-sidebar init-dropdown]]
            [psonia.app.layouts.site :as site]
            [psonia.app.panels.categories.components :refer [categories-widget]]
            [re-frame.core :as re-frame]
            [psonia.app.panels.catalog.components :refer [product-gallery product-details]]))

;; Panel
