let apiUrl = 'http://localhost:8080/api/harmony/'

export const saveHarmony = (harmony) => {
    return fetch(apiUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
            // 'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: JSON.stringify(harmony)
    })
        .then(response => response.json())
        .then(response => console.log(response))
}

export const getHarmonies = () => {
    return fetch(apiUrl)
        .then(response => response.json())
}
