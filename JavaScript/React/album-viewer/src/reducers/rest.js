import reduxApi, { transformers } from "redux-api";
import adapterFetch from "redux-api/lib/adapters/fetch";

export default reduxApi({
    albums: {
        url: "/albums?userId=(:id)",
        transformer: transformers.array,
        cache: { expire: 60 * 5 } // 5 minutes
    },
    users: {
        url: "/users",
        transformer: transformers.array,
        cache: { expire: 60 * 5 } // 5 minutes
    },
    photos: {
        url: "/photos?albumId=(:id)",
        transformer: transformers.array,
        cache: { expire: 60 * 5 } // 5 minutes
    }
})
    .use("fetch", adapterFetch(fetch))
    .use("rootUrl", "https://jsonplaceholder.typicode.com");