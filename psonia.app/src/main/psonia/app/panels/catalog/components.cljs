(ns psonia.app.panels.catalog.components
  (:require [goog.string :as gstring]
            ["drift-zoom" :default Drift]
            ["jquery" :as $]
            [re-frame.core :as re-frame]
            [psonia.app.urls :refer [resolve-href]]
            [psonia.app.panels.cart.components :refer [add-to-cart-btn]]
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
    [add-to-cart-btn
     {:button-classes ["btn-shadow" "btn-block"]
      :qty-position :top}
     product]]])

(defn- rating [val]
  [:div.star-rating
   (for [star (range (- val 1))]
     ^{:key star} [:i.sr-star.czi-star-filled.active])
   (for [no-star (range (- 6 val))]
     ^{:key no-star} [:i.sr-star.czi-star])])

(defn- product-info [{:keys [name price original-price avg-rating] :as args}]
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

(defn product-card
  ([options product]
   (fn [options product]
     [:div.card.product-card-alt
      [:div.product-thumb
       [:button.btn-wishlist.btn-sm {:type "button"}
        [:i.czi-heart]]
       [:div.product-card-actions
        [:a.btn.btn-light.btn-icon.btn-shadow.font-size-base.mx-2
         {:href (resolve-href :app.products/view {:id 1} {})}
         [:i.czi-eye]]
        [:a.btn.btn-light.btn-icon.btn-shadow.font-size-base.mx-2
         {:href "#"
          :on-click #(re-frame/dispatch [:app.cart/add-to-cart 1 product])}
         [:i.czi-cart]]]
       [:a.product-thumb-overlay]
       [:img {:alt "Product", :src "https://via.placeholder.com/550x370"}]]
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

  ([product]
   (product-card {} product)))

(defn product
  ([options product]
   (let [{:keys [id on-promotion image-b64]} product]
     [:div.card.product-card
      (when on-promotion
        [:span.badge.badge-danger.badge-shadow "Sale"])
      [:a.card-img-top.d-block.overflow-hidden
       {:href (resolve-href :app.products/view {:id 1} {})}
       [:img {:alt "Product", :src "https://via.placeholder.com/500"}]]
      [product-info product]
      [actions product]
      [:hr.d-sm-none]])))

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

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; PRODUCT CAROUSEL
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

(defn product-carousel
  ""
  [products]
  (fn []
    [:div.cz-carousel.cz-dots-enabled
     [:div.cz-carousel-inner
      {:ref (fn [el]
              (when el
                (js/tns (carousel-options el))))}
      (doall
       (for [prod products]
         ^{:key prod}
         [product-card prod]))]]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; PRODUCT GRID

(defn product-grid
  "Shows a grid of products. Can decide which product tile to use."

  ;; With options
  ([options products]
   (let [opts (merge
               {:product-type  :product
                :product-class []
                :grid-class    ["pt-3"]}
               options)]
     [:div.row.pt-3.mx-n2
      {:class (:grid-class opts)}
      (for [prod products]
        ^{:key (hash prod)}

        [:div.col-lg-3.col-md-4.col-sm-6.px-2 {:class (:product-class options)}
         (case (:product-type opts)
           :product      [product opts prod]
           :product-card [product-card opts prod])])]))

  ;; Without options
  ([products]

   (product-grid {} products)))

(defn product-grid-with-toolbox [products]
  [:section.col-lg-8
   [toolbox]
   [product-grid {:product-type  :product
                  :product-class ["mb-grid-gutter"]
                  :grid-class    ["pt-2"]}
    products]])

(defn category-banner
  "Display a banner for categories. Products are shown in a carousel"
  [products]
  (fn [products]
    [:div.row
     [:div.col-md-5
      [:div.d-flex.flex-column.h-100.overflow-hidden.rounded-lg
       {:style {:background-color "#f6f8fb"}}

       [:div.d-flex.justify-content-between.px-grid-gutter.py-grid-gutter
        [:div
         [:h3.mb-1
          "Services"]
         [:a.font-size-md
          {:href "#"}

          "Shop services"
          [:i.czi-arrow-right.font-size-xs.align-middle.ml-1]]]
        [:div.cz-custom-controls
         {:id "services-carousel"}

         [:button {:type "button"}
          [:i.czi-arrow-left]]
         [:button {:type "button"}
          [:i.czi-arrow-left]]]]

       [:a.d-none.d-md-block.mt-auto
        [:img.d-block.w-100
         ;; 2 : 3 aspect ratio
         {:src "https://via.placeholder.com/720x650/f6f8fb?text=Category+image+goes+here"}]]]]

     ;; Product grid (carousel)
     [:div.col-md-7.pt-4.pt-md-0
      [product-grid @products]]]))

;; product gallery
;; Code for video in javascript.
;; // Video thumbnail - open video in lightbox
;; for (let m = 0; m < videos.length; m++) {
;;         lightGallery(videos[m], {
;;                                  selector: 'this',
;;                                  download: false,
;;                                  videojs: true,
;;                                  youtubePlayerParams: {
;;                                                        modestbranding: 1,
;;                                                        showinfo: 0,
;;                                                        rel: 0,
;;                                                        controls: 0
;;                                                        },
;;                                  vimeoPlayerParams: {
;;                                                      byline: 0,
;;                                                      portrait: 0,
;;                                                      color: 'fe696a'
;;                                                      }
;;                                  });
;;         }

(defn init-zoom-pane
  "Initiates zoom pane to allow for zooming of images."
  [el]
  (Drift. el, #js {"paneContainer" (-> (.-parentElement el)
                                       (.querySelector ".cz-image-zoom-pane"))}))

(defn init-product-gallery
  "Initializes light gallery component given options."
  [el]
  (when el
    (let [thumbnails      (.querySelectorAll el ".cz-thumblist-item:not(.video-item)")
          previews        (.querySelectorAll el ".cz-preview-item")
          videos          (.querySelectorAll el ".cz-thumblist-item.video-item")
          activate!       #(-> (.-classList %)
                               (.add "active"))
          deactivate      #(-> (.-classList %)
                               (.remove "active"))]

      ;; Set first picture active
      (-> (aget thumbnails 0)
          (activate!))

      (-> (aget previews 0)
          (activate!))

      ;; Set click events
      (doseq [t (seq thumbnails)]
        (.addEventListener t "click" (fn [e]
                                       (this-as this
                                         (.preventDefault e)
                                         ;; Remove Active Classes
                                         (doseq [i (interleave (seq thumbnails) (seq previews))]
                                           (deactivate i))

                                         ;; Add active to selected item
                                         (activate! this)

                                         (-> (.querySelector el (.getAttribute this "href"))
                                             (activate!)))))))))


(defn product-gallery
  "Displays a gallery of the product images."
  [prod]
  (fn []
    [:div.cz-product-gallery {:ref init-product-gallery}
     ;; Preview images with zoom effect.
     [:div.cz-preview.order-sm-2
      [:div.cz-preview-item {:id "first"}
       [:img.cz-image-zoom {:src       "https://via.placeholder.com/250"
                            :data-zoom "https://via.placeholder.com/250"
                            :ref       #(when % (init-zoom-pane %))}]
       [:div.cz-image-zoom-pane]]
      [:div.cz-preview-item {:id "second"}
       [:img.cz-image-zoom {:src       "https://via.placeholder.com/250"
                            :data-zoom "https://via.placeholder.com/250"
                            :ref       #(when % (init-zoom-pane %))}]
       [:div.cz-image-zoom-pane]]
      [:div.cz-preview-item {:id "third"}
       [:img.cz-image-zoom {:src       "https://via.placeholder.com/250"
                            :data-zoom "https://via.placeholder.com/250"
                            :ref       #(when % (init-zoom-pane %))}]
       [:div.cz-image-zoom-pane]]]
     ;; Thumbnails
     [:div.cz-thumblist.order-sm-1
      [:a.cz-thumblist-item {:href "#first"}
       [:img {:src "https://via.placeholder.com/500"}]]
      [:a.cz-thumblist-item {:href "#second"}
       [:img {:src "https://via.placeholder.com/500"}]]
      [:a.cz-thumblist-item {:href "#third"}
       [:img {:src "https://via.placeholder.com/500"}]]]]))

(defn product-details
  "Shows the details of a product in a product page."
  [product]
  (fn [product]
    ;; reviews and wishlist
    [:div.product-details.ml-auto.pb-3
     ;; review section
     [:div.d-flex.justify-content-between.align-items-center.mb-2.review-details
      [:a
       {:href "#reviews"}
       [rating (:avg-rating product)]
       [:span.d-inline-block.font-size-sm.text-body.align-middle.mt-1.ml-1
        "74 Reviews"]]

      ;; Wishlist button
      [:button.btn-wishlist.mr-0.mr-lg-3
       {:type "button"
        :ref #(.tooltip  ($ %))
        :data-toggle "tooltip"
        :title ""
        :data-original-title "Add to wishlist"}
       [:i.czi-heart]]]

     ;; Price details
     [:div.mb-3.price-details
      ;; current price
      [:span.h3.font-weight-normal.text-accent.mr-1
       (:price product)]
      ;; original price
      [:del.text-muted.font-size-lg.mr-3
       (:original-price product)]
      ;; sale badge
      (when (:on-promotion product)
        [:span.badge.badge-danger.badge-shadow.align-middle.mt-n2
         "Sale"])]

     ;; Availability
     [:div.position-relative.mr-n4.mb-3
      [:div.mb-2.d-inline-block]
      [:div.product-badge.product-available.mt-n1
       [:i.czi-security-check]
       "Product Available"]]

     ;; add to cart
     [:div.mb-grid-gutter
       [add-to-cart-btn
        {:button-classes ["btn-shadow" "btn-block"]}
        product]]

     ;; Product info
     [:div#mb-4
      {:ref #(.collapse ($ %))}
      [:div.card
       [:div.card-header
         [:a.h6
          {:href "#productDescription"
           :role "button"
           :data-toggle "collapse"}
          [:i.czi-announcement.text-muted.font-size-lg.align-middle.mt-n1.mr-2]
          "Product Description"
          [:span.accordion-indicator]]]
       [:div.card-body
        [:h6.font-size-sm.mb-2
         "Some heading"]
        [:ul.font-size-sm.pl-4
         [:li "point 1"]
         [:li "point 2"]
         [:li "point 4"]]
        [:h6.font-size-sm.mb-2
         "Some second heading"]
        [:ul.font-size-sm.pl-4
         [:li "point 1"]
         [:li "point 2"]
         [:li "point 4"]]]]]]))
