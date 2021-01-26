export interface Program {
    id: string
    title: string
    excerpt: string
    day: string
    time_start: string
    time_end: string
    dj: string
    dj_image: string
    logo: string
    live: boolean
}

export interface ProgramContainer {
    status: string
    programs: any[]
}
