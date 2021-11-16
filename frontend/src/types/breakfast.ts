import { User } from './user';

export type Breakfast = {

    id: number;
    options: string;    
    date: string;
    user: User;
}


export type BreakfastPage = {
    content?: Breakfast[];
    last: boolean,
    totalPages: number;
    totalElements: number;
    number: number,
    size?: number;
    first: boolean;
    numberOfElements?: number;
    empty?: boolean;

}