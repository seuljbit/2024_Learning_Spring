console.log("Modify 접속 성공!");

document.addEventListener('click', (e) => {
    if(e.target.classList.contains('file-x')) {
        let uuid = e.target.dataset.uuid;
        removeFileToserver(uuid).then(result => {
            if(result == '1') {
                e.target.closest('li').remove();
            }
        })
    }
})

async function removeFileToserver(uuid) {
    try {
        const url = '/board/file/' + uuid;
        const config = {
            method : 'delete'
        }
        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
}
