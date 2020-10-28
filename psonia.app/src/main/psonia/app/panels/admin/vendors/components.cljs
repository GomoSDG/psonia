(ns psonia.app.panels.admin.vendors.components)

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
      created-on]
     [:td.py-3.align-middle
      [:a.nav-link-style.mr-2 {:href                "#"
                               :data-toggle         "tooltip"
                               :data-original-title "Edit"}
       [:i.czi-edit]]
      [:a.nav-link-style.mr-2.text-danger {:href                "#"
                                           :data-toggle         "tooltip"
                                           :data-original-title "Remove"
                                           :title               ""}
       [:i.czi-trash]]]]))



(defn vendor-list [vendors]
  (fn []
    [:table.table.table-hover.mb-0
     [:thead
      [:tr
       [:th "Name"]
       [:th "Active"]
       [:th "Created On"]
       [:th]]]
     [:tbody
      (for [i vendors]
        ^{:key (:id i)} [vendor-list-item i])]]))
