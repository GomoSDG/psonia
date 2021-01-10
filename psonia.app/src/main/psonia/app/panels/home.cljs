(ns psonia.app.panels.home
  (:require [psonia.app.panels.components :refer [multi-level-navbar static-sidebar init-dropdown]]
            [psonia.app.layouts.site :as site]
            [re-frame.core :as re-frame]
            [psonia.app.panels.catalog.components :refer [featured-product]]
            [reagent.ratom :as ratom]))

(def breadcrumb [{:name "Home"
                  :url  "#"
                  :icon :home}])

(defn carousel-options
  ([el]
   (carousel-options el {}))
  ([el options]
   (clj->js {"container" el
             "controls" false
             "navPosition" "bottom"
             "mouseDrag" true
             "speed" 5
             "items" 3
             "gutter" 15
             "autoplayHoverPause" true
             "autoplayButtonOutput" false
             "responsive" {"0" {"items" 1}
                           "500" {"items" 2}
                           "768" {"items" 3}
                           "992" {"items" 3
                                  "gutter" 30}}})))

(defn panel [navbar]
  (let [products (re-frame/subscribe [:products])]
    (fn []
      [:<>
       [multi-level-navbar]
       [:section.bg-accent.bg-position-center-top.bg-no-repeat.py-5
        [:div.pb-lg-5.mb-lg-3
         [:div.container.py-lg-5.my-lg-5
          [:div.row.mb-4.mb-sm-5
           [:div.col-lg-7.col-md-9.text-center.text-sm-left
            [:h1.text-white.line-height-base
             "You know what " [:span.strong "you "] "want?"]
            [:h2.h5.text-white.font-weight-light
             "Give it a search below!"]]]
          [:div.row.pb-lg-5.mb-sm-5
           [:div.col-lg-6.col-md-8
            [:div.input-group.input-group-overlay.input-group.lg
             [:div.input-group-prepend-overlay
              [:span.input-group-text
               [:i.czi-search]]]
             [:input.form-control.form-control-lg.prepended-form-control.rounded-right-0
              {:type "text"
               :placeholder "Start your search"}]
             [:div.input-group-append
              [:button.btn.btn-primary.btn-lg.dropdown-toggle.font-size-base
               {:type "button"
                :ref init-dropdown}
               "All Categories"]
              [:div.dropdown-menu.dropdown-menu-right
               [:a.dropdown-item
                {:href "#"}
                "All Categories"]
               [:a.dropdown-item
                {:href "#"}
                "Products"]
               [:a.dropdown-item
                {:href "#"}
                "Services"]]]]]]]]]
       [:div.container.mt-lg-n10.bg-light.box-shadow-lg.rounded-lg.overflow-hidden
        [:div.row
         [static-sidebar
          [{:name  "Something"
            :items [{:name   "Hello World"
                     :icon   "czi-home"
                     :href   "#"
                     :active true}
                    {:name "Hello to you too"
                     :href "#"}]}]]
         [:section.position-relative.pt-3.pt-lg-0.pb-5.mt-2.col-lg-8
          [:div.px-lg-2.border-0.product-card-alt
           [:div.px-4.pt-5.pb-4
            [:h2.h3.text-center "What's selling"]

            ;; Carousel
            [:div.cz-carousel.cz-dots-enabled
             [:div.cz-carousel-inner
              {:ref (fn [el]
                      (when el
                        (js/tns (carousel-options el))))}
              (doall
               (for [prod (take 5 @products)]
                 ^{:key prod} [featured-product prod]))]]]]]]

        [:div.row [:h1.offset-lg-4 "Hello wolrd!"]]]])))


