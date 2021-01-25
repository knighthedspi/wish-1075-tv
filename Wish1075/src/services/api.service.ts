import { PostContainer } from '../models/post'
import { get } from './http'

const getHls = () => {
    return get<PostContainer>('https://www.wish1075.com/api/get_recent_posts/?post_type=streaming')
}

export { getHls }