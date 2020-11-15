import { getCommentsByPostIdCached, getPostsByUserIdCached, getAlbumsByUserIdCached, getPhotosByAlbumIdCached } from '../../actions'
import CreateActivityCounter from './CreateActivityCounter'
import CreateJoinActivityCounter from './CreateJoinActivityCounter'


export const CommmentsCounter = CreateActivityCounter('commentsByPostId', getCommentsByPostIdCached);
export const UserPostCounter = CreateActivityCounter("postsByUserId", getPostsByUserIdCached);
export const UserAlbumsCounter = CreateActivityCounter("albumsByUserId", getAlbumsByUserIdCached);

export const UserCommentsCounter = CreateJoinActivityCounter("postsByUserId", getPostsByUserIdCached, "commentsByPostId", getCommentsByPostIdCached);
export const UserPhotosCounter = CreateJoinActivityCounter("albumsByUserId", getAlbumsByUserIdCached, "photosByAlbumId", getPhotosByAlbumIdCached);
