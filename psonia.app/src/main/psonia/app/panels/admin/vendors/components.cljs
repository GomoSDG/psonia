(ns psonia.app.panels.admin.vendors.components)

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

(defn vendor-list-item [item]
  (let [{name       :name
         active     :active
         created-on :created-on} item]

    [:tr
     [:td.py-3.align-middle
      [:div.media.align-items-center
       [:img.mr-2 {:src "https://via.placeholder.com/20"}]
       [:div.media-body
        name]]]
     [:td.py-3.align-middle
      active]
     [:td.py-3.align-middle
      created-on]]))

(defn vendor-list [vendors]
  (fn []
    [:table.table.table-hover.mb-0
     [:thead
      [:tr
       [:th "Name"]
       [:th "Active"]
       [:th "Created On"]]]
     [:tbody
      (for [i vendors]
        ^{:key (:id i)} [vendor-list-item i])]]))
