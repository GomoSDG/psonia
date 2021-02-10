(ns psonia.panels.home
  (:require [psonia.panels.components :refer [multi-level-navbar init-dropdown]]
            [re-frame.core :as re-frame]
            [psonia.panels.catalog.components :refer [product-carousel] :as catalog]))

(def breadcrumb [{:name "Home"
                  :url  "#"
                  :icon :home}])

(defn ^{:page-title "Home"} panel []
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
              {:type        "text"
               :placeholder "Start your search"}]
             [:div.input-group-append
              [:button.btn.btn-primary.btn-lg.dropdown-toggle.font-size-base
               {:type "button"
                :ref  init-dropdown}
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

       [:section.container.position-relative.pt-3.pt-lg-0.pb-5.mt-lg-n10
        [:div.px-lg-2.border-0.box-shadow.card
         [:div.px-4.pt-5.pb-4.card-body
          [:h2.h3.text-center "What's selling"]

          ;; Carousel
          [product-carousel @products]]]]

       ;; Recent products by category
       [:section.container.pb-5.mb-lg-3
        ;; Heading
        [:div.d-flex.flex-wrap.justify-content-between.align-items-center.pt-1.border-bottom.pb-4.mb-4
         [:h2.h3.mb-0.pt-3.mr-2
          "Just came in"]
         [:div.pt-3
          [:select.custom-select
           [:option
            "All categories"]
           [:option
            "Products"]
           [:option
            "Services"]]]]

        [:div.row.pt-2.mx-n2
         [catalog/product-grid
          {:product-type  :product-card
           :product-class ["mb-grid-gutter"]
           :grid-class    ["pt-2"]}
          (take 8 (cycle @products))]]
        [:div.text-center
         [:a.btn.btn-outline-accent
          "View more"
          [:i.czi-arrow-right.font-size-ms.ml-1]]]]

       [:section.border-top.py-5
        [:div.container.py-lg-2
         [:h2.h3.mb-3.pb-3.pb-lg-4.text-center
          "Seller of the month"]

         [:div.row
          [:div.col-lg-4.text-center.text-lg-left.pb-3.pt-lg-2
           ;; Vendor
           [:div.d-inline-block.text-left
            [:div.media.media-ie-fix.align-items-center.pb-3
             [:div.img-thumbnail.rounded-circle.positive-relative
              {:style {:width "6.375rem"}}

              [:img.rounded-circle {:src "https://via.placeholder.com/180?text=Seller"}]]
             [:div.media-body.pl-3
              [:h3.font-size-lg.mb-0
               "Seller Name"]
              [:span.d-block.text-muted.font-size-ms.pt-1.pb-2
               "Member since November 2020"]
              [:a.btn.btn-primary.btn-sm
               "View products"]]]]]
          ;; Vendor products/services
          [:div.col-lg-8
           [product-carousel (take 3 @products)]]]]]

       ])))
