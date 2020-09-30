(ns psonia.app.panels.admin.vendors.core)

(defn page-title []
  [:div.page-title-overlap.bg-accent.pt-4
   [:div.container.d-flex.flex-wrap.flex-sm-nowrap.justify-content-center.justify-content-sm-between.align-items-center.pt2
    [:div [:h1 "Hello"]]]])

(defn list-all []
  [:div
   [page-title]])

