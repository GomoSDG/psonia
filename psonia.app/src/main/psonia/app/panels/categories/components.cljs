(ns psonia.app.panels.categories.components)

(defn- compact-categories []
  [:div.card
   [:div.card-header
    [:h3.accordion-heading
     [:a.collapsed
      {:aria-controls "sunglasses",
       :aria-expanded "false",
       :data-toggle "collapse",
       :role "button",
       :href "#sunglasses"}
      "\n            Sunglasses\n            "
      [:span.accordion-indicator]]]]
   [:div#sunglasses.collapse
    {:data-parent "#shop-categories"}
    [:div.card-body [:div.widget.widget-links]]]])

(defn- search []
  [:div.input-group-overlay.input-group-sm.mb-2
   [:input.cz-filter-search.form-control.form-control-sm.appended-form-control
    {:placeholder "Search", :type "text"}]
   [:div.input-group-append-overlay
    [:span.input-group-text [:i.czi-search]]]])

(defn- sub-categories [categories]
  [:ul.widget-list.cz-filter-list.pt-1
   {:data-simplebar-auto-hide "false",
    :data-simplebar "data-simplebar",
    :style {:height "12rem"}}
   (for [{:keys [name total id], :as cat} categories]
     [:li.widget-list-item.cz-filter-item
      [:a.widget-list-link.d-flex.justify-content-between.align-items-center
       {:href "#"}
       [:span.cz-filter-item-text name]
       [:span.font-size-xs.text-muted.ml-3 total]]])])

(defn- compact-sub-categories [categories]
  [:ul.widget-list
   [:li.widget-list-item
    [:a.widget-list-link.d-flex.justify-content-between.align-items-center
     {:href "#"}
     [:span "View all"]
     [:span.font-size-xs.text-muted.ml-3 "1,842"]]]
   [:li.widget-list-item
    [:a.widget-list-link.d-flex.justify-content-between.align-items-center
     {:href "#"}
     [:span "Fashion Sunglasses"]
     [:span.font-size-xs.text-muted.ml-3 "953"]]]
   [:li.widget-list-item
    [:a.widget-list-link.d-flex.justify-content-between.align-items-center
     {:href "#"}
     [:span "Sport Sunglasses"]
     [:span.font-size-xs.text-muted.ml-3 "589"]]]])

(defn- categories [categories]
  [:div.card
   [:div.card-header
    [:h3.accordion-heading
     [:a
      {:aria-controls "clothing",
       :aria-expanded "true",
       :data-toggle "collapse",
       :role "button",
       :href "#clothing"}
      "Clothing"
      [:span.accordion-indicator]]]]
   [:div#clothing.collapse.show
    {:data-parent "#shop-categories"}
    [:div.card-body
     [:div.widget.widget-links.cz-filter
      [search]
      [sub-categories categories]]]]])

(defn categories-widget []
  [:div.widget.widget-categories
   [:h3.widget-title "Categories"]
   [:div#shop-categories.accordion
    [categories []]]])
