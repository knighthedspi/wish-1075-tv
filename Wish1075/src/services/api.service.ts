import { PostContainer } from '../models/post'
import { ProgramContainer } from '../models/program'
import { get } from './http'

const getHls = () => {
    return get<PostContainer>('https://www.wish1075.com/api/get_recent_posts/?post_type=streaming')
}

const getPrograms = (day: string) => {
    return get<ProgramContainer>(`https://www.wish1075.com/api/programs/get_programs_by_day/?day=${day}`)
}

export { getHls, getPrograms }

// pattern using (localized) text not implemented, use js-joda-locale plugin