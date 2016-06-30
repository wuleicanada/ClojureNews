(ns route.comment-entry
  (:require-macros [secretary.core :refer [defroute]])
  (:require [controller.comment-entry :as controller]
            [secretary.core]))

(defroute get-story-comment-by-id "/comment/:id" [id]
          (controller/get-story-comment-by-id id))

(defroute edit-story-comment-by-id "/comment/edit/:id" [id]
          (controller/edit-story-comment-by-id id))