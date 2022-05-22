let apiUrl = 'http://localhost:8080/api/playlist/'

export const getPlaylists = () => {
    return fetch(apiUrl)
        .then(response => response.json())
}

export const createPlaylist = () => {
    return fetch(apiUrl)
        .then(response => response.json())
}

