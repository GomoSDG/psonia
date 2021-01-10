(ns psonia.app.panels.catalog.components
  (:require [goog.string :as gstring]
            [goog.string.format]))

;; Define feature types and customize

(defn- features []
  [:div.card-body.card-body-hidden
    [:div.text-center.pb-2
     [:div.custom-control.custom-option.custom-control-inline.mb-2
      [:input#white.custom-control-input
       {:checked "checked", :name "color1", :type "radio"}]
      [:label.custom-option-label.rounded-circle
       {:for "white"}
       [:span.custom-option-color.rounded-circle
        {:style "background-color: #eaeaeb;"}]]]
     [:div.custom-control.custom-option.custom-control-inline.mb-2
      [:input#blue.custom-control-input
       {:name "color1", :type "radio"}]
      [:label.custom-option-label.rounded-circle
       {:for "blue"}
       [:span.custom-option-color.rounded-circle
        {:style "background-color: #d1dceb;"}]]]
     [:div.custom-control.custom-option.custom-control-inline.mb-2
      [:input#yellow.custom-control-input
       {:name "color1", :type "radio"}]
      [:label.custom-option-label.rounded-circle
       {:for "yellow"}
       [:span.custom-option-color.rounded-circle
        {:style "background-color: #f4e6a2;"}]]]
     [:div.custom-control.custom-option.custom-control-inline.mb-2
      [:input#pink.custom-control-input
       {:name "color1", :type "radio"}]
      [:label.custom-option-label.rounded-circle
       {:for "pink"}
       [:span.custom-option-color.rounded-circle
        {:style "background-color: #f3dcff;"}]]]]])

(defn- actions [product]
  [:div.card-body.card-body-hidden
   [:div.text-center.pb-2
    [:select.custom-select.custom-select-sm.mr-2
     [:option "XS"]
     [:option "S"]
     [:option "M"]
     [:option "L"]
     [:option "XL"]]]
   [:button.btn.btn-primary.btn-sm
    {:type "button"}
    [:i.czi-cart.font-size-sm.mr-1] "Add to Cart"]])

(defn- rating [val]
  [:div.star-rating
   (for [star (range (- val 1))]
     ^{:key star} [:i.sr-star.czi-star-filled.active])
   (for [no-star (range (- 6 val))]
     ^{:key no-star} [:i.sr-star.czi-star])])

(defn- product-details [{:keys [name price original-price avg-rating]}]
  [:div.card-body.py-2
   [:a.product-meta.d-block.font-size-xs.pb-1
    {:href "#"}
    name]
   [:h3.product-title.font-size-sm
    [:a {:href "#"} name]]
   (if avg-rating
     [rating avg-rating])
   [:div.d-flex.justify-content-between
    [:div.product-price
     [:span.text-accent
      "R" (gstring/format "%.2f" price)]
     (when original-price
       [:del.font-size-sm.text-muted "R" (gstring/format "%.2f" original-price)])]]])

(defn featured-product [product]
  (fn [product]
    [:div.card.product-card-alt
     [:div.product-thumb
      [:button.btn-wishlist.btn-sm {:type "button"}
       [:i.czi-heart]]
      [:div.product-card-actions
       [:a.btn.btn-light.btn-icon.btn-shadow.font-size-base.mx-2 {:href "#"}
        [:i.czi-eye]]
       [:a.btn.btn-light.btn-icon.btn-shadow.font-size-base.mx-2 {:href "#"}
        [:i.czi-cart]]]
      [:a.product-thumb-overlay]
      [:img {:alt "Product", :src "https://via.placeholder.com/500"}]]
     [:div.card-body
      [:div.d-flex.flex-wrap.justify-content-between.align-items-start.pb-2
       [:div.text-muted.font-size-xs.mr-1
        "by "
        [:a.product-meta.font-weight-medium {:href "#"}
         "Vendor Name"]
        " in "
        [:a.product-meta.font-weight-medium {:href "#"}
         "Services"]]
       [:div.star-rating
        [rating (:avg-rating product)]]]
      [:h3.product-title.font-size-sm.mb-2
       [:a {:href "#"}
        (:name product)]]
      [:div.d-flex.flex-wrap.justify-content-between.align-items-center
       [:div.font-size-sm.mr-2
        [:i.czi-money-bag
         "500"
         [:span.font-size-xs.ml-1
          "Sales"]]]
       [:div.bg-faded-accent.text-accent.rounded-sm.py-1.px-2
        (str "R"(:price product))]]]]))

(defn product [product]
  (let [{:keys [id on-promotion image-b64]} product]
    [:div.card.product-card
     (when on-promotion
       [:span.badge.badge-danger.badge-shadow "Sale"])
     [:a.card-img-top.d-block.overflow-hidden
      {:href "#"}
      [:img {:alt "Product", :src "https://via.placeholder.com/500"}]]
     [product-details product]
     [actions product]
     [:hr.d-sm-none]]))

(defn toolbox []
  [:div.d-flex.justify-content-center.justify-content-sm-between.align-items-center.pt-2.pb-4.pb-sm-5
    [:div.d-flex.flex-wrap
     [:div.form-inline.flex-nowrap.mr-3.mr-sm-4.pb-3
      [:label.text-light.opacity-75.text-nowrap.mr-2.d-none.d-sm-block
       {:for "sorting"}
       "Sort by:"]
      [:select#sorting.form-control.custom-select
       [:option "Popularity"]
       [:option "Low - Hight Price"]
       [:option "High - Low Price"]
       [:option "Average Rating"]
       [:option "A - Z Order"]
       [:option "Z - A Order"]]
      [:span.font-size-sm.text-light.opacity-75.text-nowrap.ml-2.d-none.d-md-block
       "of 287 products"]]]
    [:div.d-flex.pb-3
     [:a.nav-link-style.nav-link-light.mr-3
      {:href "#"}
      [:i.czi-arrow-left]]
     [:span.font-size-md.text-light "1 / 5"]
     [:a.nav-link-style.nav-link-light.ml-3
      {:href "#"}
      [:i.czi-arrow-right]]]
    [:div.d-none.d-sm-flex.pb-3
     [:a.btn.btn-icon.nav-link-style.bg-light.text-dark.disabled.opacity-100.mr-2
      {:href "#"}
      [:i.czi-view-grid]]
     [:a.btn.btn-icon.nav-link-style.nav-link-light
      {:href "shop-list-ls.html"}
      [:i.czi-view-list]]]])

(defn product-grid [products]
  [:div.row.pt-3.mx-n2
   (for [prod products]
     ^{:key prod}[:div.col-lg-3.col-md-4.col-sm-6.px-2.mb-4
                  [product prod]])])

(defn product-grid-with-toolbox [products]
  [:section.col-lg-8
   [toolbox]
   [product-grid products]])

