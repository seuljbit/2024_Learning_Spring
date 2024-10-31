console.log("BoardDetailComment.js");
console.log("vscode 연동 성공!");

document.getElementById('cmtAddBtn').addEventListener('click',()=>{
    const cmtText = document.getElementById('cmtText');
    const cmtWriter = document.getElementById('cmtWriter');

    if(cmtText.value == null || cmtText.value == '') {
        alert("댓글을 입력해 주세요.");
        cmtText.focus();
        return false;
    }

    let cmtData = {
        bno : bnoVal,
        writer : cmtWriter.value,
        content : cmtText.value
    }
    console.log(cmtData);

    postCommentToServer(cmtData).then(result => {
        if (result == '1') {
            alert("댓글을 작성하였습니다.");
            cmtText.value = "";
            spreadCommentList(bnoVal);
        }
    });
});

function spreadCommentList(bno, page=1) { // 댓글 뿌리기

    getCommentListFromServer(bno, page).then(result => {
        console.log(result);
    
        const ul = document.getElementById('cmtListArea');
        if (result.length > 0) {
            ul.innerHTML = ""; // 기존 댓글 제거
            ul.style.display = "block"; // 댓글이 있을 때는 ul 보이기
    
            for (let cvo of result) {
                let li = `<li class="list-group-item" data-cno=${cvo.cno}>`;
                li += `<div class="d-flex justify-content-between align-items-center mb-2">`;
                li += `<span class="fw-bold text-primary">${cvo.writer}</span>`;
                li += `<span class="badge badge-primary">${cvo.regDate}</span>`;
                li += `</div>`;
                li += `<p class="mb-0">${cvo.content}</p>`;
                
                // 수정 및 삭제 버튼 추가
                li += `<div class="d-flex justify-content-end mt-2">`;
                li += `<button type="button" data-cno=${cvo.cno} class="btn btn-outline-secondary btn-sm mr-2 mod" data-bs-toggle="modal" data-bs-target="#myModal">수정</button>`;
                li += `<button type="button" data-cno=${cvo.cno} class="btn btn-outline-danger btn-sm del">삭제</button>`;
                li += `</div>`;
                li += `</li>`;
                ul.innerHTML += li;
            }
        } else { ul.style.display = "none"; }// 댓글이 없을 때는 ul 숨기기 
    });    
}

async function updateCommentToServer(cmtData) {
    try {
        const url = "/comment/modify";
        const config = {
            method : 'put',
            headers : { 'Content-Type' : 'application/json; charset=utf-8' },
            body : JSON.stringify(cmtData)
        }
        
        const resp = await fetch(url, config);
        console.log(resp);
        const result = await resp.text(); // isOk

        return result;

    } catch (error) {
        console.log(error);
    }
}

// delete 메서드 사용
async function removeCommentToServer(cno) {
    try {
        const url = "/comment/" + cno;
        const config = { method : 'delete' }
        const resp = await fetch(url, config);
        const result = await resp.text();
        
        return result;

    } catch (error) {
        console.log(error);
    }
}

// 수정, 삭제
document.addEventListener('click', (e) => {
    const target = e.target;

    // 내가 클릭한 버튼의 객체를 모달창으로 전달
    if(target.classList.contains('mod')) {
        // 내가 클릭한 버튼의 li 가져오기
        let li = target.closest('li'); // closest : 가장 가까운 태그(나를 감싸고 있는 부모태그) 찾기

        // nextSiblvling : 한 부모 안에서의 다음 형제 값 찾기
        let cmtText = li.querySelector('.mb-0').innerText;
        console.log(cmtText);
        document.getElementById('cmtTextMod').value = cmtText;

        let cmtWriter = li.querySelector('.text-primary').innerText;
        document.getElementById('cmtWriterMod').innerHTML = `<b>${cmtWriter}</b> 님의 댓글 내용 수정`;

        // cmtModBtn -> cno 값을 dataset으로 달기
        let cno = li.dataset.cno;
        document.getElementById('cmtModBtn').setAttribute("data-cno", cno);
    }

    if(target.id == 'cmtModBtn') {
        let cmtData = {
            cno : target.dataset.cno,
            content : document.getElementById('cmtTextMod').value
        }
        console.log(cmtData);

        updateCommentToServer(cmtData).then(result => {
            if (result == '1') {
                alert("댓글을 수정하였습니다.");
            } else {
                alert("댓글 수정에 실패했습니다.")
            }
            document.querySelector('.btn-close').click(); // 모달창 닫기 : btn-close 라는 객체를 클릭해라
            spreadCommentList(bnoVal); // 댓글 뿌리기
        })
    }
    
    if(target.classList.contains('del')) {
        // cno만 있으면 됨
        let li = target.closest('li')
        let cno = li.dataset.cno;

        removeCommentToServer(cno).then(result => {
            if (result == '1') {
                alert("댓글을 삭제했습니다.");
            } else {
                alert("댓글 삭제를 실패했습니다.")
            }
            spreadCommentList(bnoVal); // 댓글 뿌리기
        })
    }

    if(target.id == 'moreBtn') {
        let page = target.dataset.page;
    }
})

// get은 생략 가능
async function getCommentListFromServer(bno, page) {
    try {
        const resp = await fetch("/comment/" + bno + "/" + page);
        const result = await resp.json();

        return result;

    } catch (error) {
        console.log(error);
    }
}

async function postCommentToServer(cmtData) {
    try {
        const url = "/comment/post";
        const config = {
            method : "post",
            headers : { 'Content-Type' : 'application/json; charset=utf-8' },
            body : JSON.stringify(cmtData)
        };

        const resp = await fetch(url, config);
        console.log(resp);
        const result = await resp.text(); // isOk

        return result;
        
    } catch (error) {
        console.log(error);
    }
};