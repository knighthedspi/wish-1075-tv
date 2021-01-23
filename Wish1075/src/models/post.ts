export interface Post {
    id: string;
    type: string;
    slug: string;
    url: string;
    status: string;
    title: string;
    title_plain: string;
    content: string;
    excerpt: string;
    date: string;
    modified: string;
}

export interface PostContainer {
    status: string;
    count: number;
    count_total: number;
    page: number;
    posts: Post[];
}
