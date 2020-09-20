(ns psonia.app.panels.catalog.components)

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
  [:div.d-flex.mb-2
   [:select.custom-select.custom-select-sm.mr-2
    [:option "XS"]
    [:option "S"]
    [:option "M"]
    [:option "L"]
    [:option "XL"]]
   [:button.btn.btn-primary.btn-sm
    {:type "button"}
    [:i.czi-cart.font-size-sm.mr-1] "Add to Cart"]])

(defn- rating [val]
  [:div.star-rating
   (for [star (range (- val 1))]
     [:i.sr-star.czi-star-filled.active])
   (for [no-start (range (- 6 val))]
     [:i.sr-star.czi-star])])

(defn- product-details [{:keys [name price original-price avg-rating]}]
  [:div.card-body.py-2
   [:a.product-meta.d-block.font-size-xs.pb-1
    {:href "#"}
    name]
   [:h3.product-title.font-size-sm
    [:a {:href "#"} name]]
   [:div.d-flex.justify-content-between
    [:div.product-price
     [:span.text-accent "R" (.toLocaleString price "en-ZA")]
     (when original-price
       [:del.font-size-sm.text-muted "R" original-price])]
    (if avg-rating
      [rating avg-rating])]])

(defn product [product]
  (let [{:keys [id on-promotion image-b64]} product]
    [:div.card.product-card
     (when on-promotion
       [:span.badge.badge-danger.badge-shadow "Sale"])
     [:a.card-img-top.d-block.overflow-hidden
      {:href "#"}
      [:img {:alt "Product", :src "https://via.placeholder.com/500"}]]
     [product-details product]
     [actions product]]))

(defn product-grid [products]
  [:div.row.pt-2.mx-n2
   (for [prod products]
     [:div.col-lg-3.col-md-4.col-sm-6.px-2.mb-4
      [product prod]])])

