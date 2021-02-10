(ns psonia.layouts.site
  (:require [re-frame.core :as re-frame]
            [psonia.panels.core :as panels]))

(def icons {:add "czi-add-circle"
            :home "czi-home"})

(defn nav-item [body]
  [:li.nav-item
   body])

(defn navbar-nav []
  [:ul.navbar-nav
   [:li.nav-item "Home"]])

(defn brand [body]
  [:<>
   [:a.navbar-brand.d-none.d-sm-block.mr-3.flex-shrink-0 {:style {:min-width "7rem"}
                                                          :href "/"}
    body]
   [:a.navbar-brand.d-sm-none.mr-2.order-lg-1 {:style {:min-width "4.625rem"}
                                               :href "/"}
    body]])

(defn navbar []
   [:div#navbarCollapse.collapse.navbar-collapse
    [:hr.my-3]
    [navbar-nav]])



(defn sidebar [& el]
  [:div#shop-sidebar.cz-sidebar.rounded-lg.box-shadow-lg
   [:div.cz-sidebar-header.box-shadow-sm
    [:button.close.ml-auto
     {:aria-label "Close", :data-dismiss "sidebar", :type "button"}
     [:span.d-inline-block.font-size-xs.font-weight-normal.align-middle
      "Close sidebar"]
     [:span.d-inline-block.align-middle.ml-2
      {:aria-hidden "true"}
      "×"]]]
   [:div.cz-sidebar-body
    (for [e el]
      e)]])


(defn page-title [title breadcrumb]
  [:div.page-title-overlap.bg-dark.pt-4
   [:div.container.d-lg-flex.justify-content-between.py-2.py-lg-3
    [:div.order-lg-2.mb-3.mb-lg-0.pt-lg-2
     [:nav
      {:aria-label "breadcrumb"}
      [:ol.breadcrumb.breadcrumb-light.flex-lg-nowrap.justify-content-center.justify-content-lg-start
       (for [{:keys [url name icon], :as b} breadcrumb]
         ^{:key b} [:li.breadcrumb-item
                    [:a.text-nowrap {:href url} [:i {:class (icons icon)}] name]])]]]
    [:div.order-lg-1.pr-lg-4.text-center.text-lg-left
     [:h1.h3.text-light.mb-0 title]]]])

(defn site-layout []
  (let [panel  (re-frame/subscribe [:active-panel])
        title  (panels/page-title @panel)
        set-tile (set! (.-title js/document) (str title " - Super Street"))]
    (fn []
      [@panel navbar])))


