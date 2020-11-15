import { RSAA } from "redux-api-middleware";
import { checkCacheValid } from "redux-cache";

import fetchLimited from "../utils/fetch";

import { createAction } from "redux-actions";

export const restTriple = ["REST_REQUEST", "REST_SUCCESS", "REST_FAILURE"].map(
    type => createAction(type, () => undefined, params => ({ params }))
);

export const restItemTriple = [
    "REST_ITEM_REQUEST",
    "REST_ITEM_SUCCESS",
    "REST_ITEM_FAILURE"
].map(type => createAction(type, () => undefined, params => ({ params })));

function restActionTypesForKey(key) {
    return restTriple.map(func => func({ key }));
}

function restActionTypesForItemKey(key, id) {
    return restItemTriple.map(func => func({ key, id }));
}

const createCacheableItemAction = (actionCreator, field) => {
    return (id, ...args) => (dispatch, getState) => {
        const isCacheValid = checkCacheValid(() => getState().rest.field, id);
        if (isCacheValid) {
            return;
        }
        dispatch(actionCreator(id, ...args));
    };
};

const createCacheableAction = (actionCreator, field) => {
    return (...args) => (dispatch, getState) => {
        const isCacheValid = checkCacheValid(() => getState().rest, field);
        if (isCacheValid) {
            return;
        }
        dispatch(actionCreator(...args));
    };
};

export const getPosts = () => ({
    [RSAA]: {
        endpoint: "https://jsonplaceholder.typicode.com/posts/",
        method: "GET",
        headers: { "Content-Type": "application/json" },
        types: restActionTypesForKey("posts"),
        fetch: fetchLimited
    }
});

export const getUsers = () => ({
    [RSAA]: {
        endpoint: "https://jsonplaceholder.typicode.com/users/",
        method: "GET",
        headers: { "Content-Type": "application/json" },
        types: restActionTypesForKey("users"),
        fetch: fetchLimited
    }
});

export const getUsersById = id => ({
    [RSAA]: {
        endpoint: "https://jsonplaceholder.typicode.com/users/" + id,
        method: "GET",
        headers: { "Content-Type": "application/json" },
        types: restActionTypesForItemKey("usersById", id),
        fetch: fetchLimited
    }
});

export const getPostsByUserId = id => ({
    [RSAA]: {
        endpoint: "https://jsonplaceholder.typicode.com/posts?userId=" + id,
        method: "GET",
        headers: { "Content-Type": "application/json" },
        types: restActionTypesForItemKey("postsByUserId", id),
        fetch: fetchLimited
    }
});

export const getCommentsByPostId = id => ({
    [RSAA]: {
        endpoint: "https://jsonplaceholder.typicode.com/comments?postId=" + id,
        method: "GET",
        headers: { "Content-Type": "application/json" },
        types: restActionTypesForItemKey("commentsByPostId", id),
        fetch: fetchLimited
    }
});

export const getAlbumsByUserId = id => ({
    [RSAA]: {
        endpoint: "https://jsonplaceholder.typicode.com/albums?userId=" + id,
        method: "GET",
        headers: { "Content-Type": "application/json" },
        types: restActionTypesForItemKey("albumsByUserId", id),
        fetch: fetchLimited
    }
});

export const getPhotosByAlbumId = id => ({
    [RSAA]: {
        endpoint: "https://jsonplaceholder.typicode.com/photos?albumId=" + id,
        method: "GET",
        headers: { "Content-Type": "application/json" },
        types: restActionTypesForItemKey("photosByAlbumId", id),
        fetch: fetchLimited
    }
});

export const getUsersByIdCached = createCacheableItemAction(
    getUsersById,
    "usersById"
);
export const getPostsByUserIdCached = createCacheableItemAction(
    getPostsByUserId,
    "postsByUserId"
);
export const getCommentsByPostIdCached = createCacheableItemAction(
    getCommentsByPostId,
    "commentsByPostId"
);
export const getAlbumsByUserIdCached = createCacheableItemAction(
    getAlbumsByUserId,
    "albumsByUserId"
);
export const getPhotosByAlbumIdCached = createCacheableItemAction(
    getPhotosByAlbumId,
    "photosByAlbumId"
);

export const getPostsCached = createCacheableAction(getPosts, "posts");
export const getUsersCached = createCacheableAction(getUsers, "users");
