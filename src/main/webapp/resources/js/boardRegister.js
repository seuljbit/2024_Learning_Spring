console.log("boardRegister.js 연동 성공~!")

// 트리거 버튼 처리
document.getElementById('trigger').addEventListener('click', () => {
    document.getElementById('file').click();
});

// 실행 파일에 대한 정규표현식 작성 / 파일 최대 사이즈 20MB
const regExp = new RegExp("\.(exe|jar|msi|dll|sh|bat)$");
const maxSize = 1024*1024*20;

function fileValidation(fileName, fileSize) {
    if(regExp.test(fileName)) {
        return 0;
    } else if(fileSize > maxSize) {
        return 0;
    } else {
        return 1;
    }
}

document.addEventListener('change', (e) => {
    console.log(e.target);
    if(e.target.id == 'file') { // 파일창에 변화가 생겼다면?
        // input type="file" element에 저장된 file 정보를 가져오는 property files
        const fileObject = document.getElementById('file').files;
        console.log(fileObject);
        
        document.getElementById('regBtn').disabled = false;


        let div = document.getElementById('fileZone');
        div.innerHTML = ""; // 새로 추가되는 목록이 있다면 삭제

        let isOk = 1; // 모든 파일이 검증을 통과해야만 register 버튼을 활성화

        let ul = `<ul class="list-group list-group-flush">`;
        
        for(let file of fileObject) {
            let validResult = fileValidation(file.name, file.size); // 개별 파일에 대한 검증 결과
            isOk *= validResult;
            ul += `<li class="list-group-item">`;
            ul += `<div class="d-flex justify-content-between align-items-center mb-2">`;
            ul += validResult 
                ? `<span class="fw-bold text-success"> O </span>` 
                : `<span class="fw-bold text-danger"> X </span>`;
            ul += `<span class="badge bg-${validResult ? 'success' : 'danger'}">${file.name}</span>`;
            ul += `<span class="badge bg-secondary">${(file.size / 1024).toFixed(2)} Bytes </span>`;
            ul += `</div>`;
            ul += `</li>`;
        }
        ul += `</ul>`;
        
        div.innerHTML = ul;
        
        // 모든 파일이 검증을 통과했을 때만 등록 버튼 활성화
        if(isOk == 0) {
            document.getElementById('regBtn').disabled = true;
        }
    }
})