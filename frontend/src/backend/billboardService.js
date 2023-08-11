import {appFetch, config} from "./appFetch";

export const getBillboard = (date, onSuccess, onErrors) =>
    appFetch(`/cinema/billboard?date=${date}`, config('GET'),onSuccess,onErrors);
export const getMovieById = (id, onSuccess, onErrors) =>
    appFetch(`/cinema/movie/${id}`, config('GET'),onSuccess,onErrors);
export const getSessionById = (id, onSuccess, onErrors) =>
    appFetch(`/cinema/session/${id}`, config('GET'),onSuccess,onErrors);
