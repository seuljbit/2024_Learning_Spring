// 기존 코드 유지 + 누락된 부분 수정 및 추가

console.log("boardDetailComment.js in");
console.log("vscode 연동 성공");

document.getElementById('cmtAddBtn').addEventListener('click', () => {
    const cmtText = document.getElementById('cmtText');
    const cmtWriter = document.getElementById('cmtWriter');

    if (cmtText.value == null || cmtText.value == '') {
        alert('댓글을 입력해주세요.');
        cmtText.focus();
        return false;
    }

    let cmtData = {
        bno: bnoVal,
        writer: cmtWriter.value,
        content: cmtText.value
    };
    console.log(cmtData);

    postCommentToServer(cmtData).then(result => {
        if (result == '1') {
            alert("댓글 등록 성공");
            cmtText.value = "";
            spreadCommentList(bnoVal);
        }
    });
});

function spreadCommentList(bno, page = 1) { // 댓글 뿌리기
    getCommentListFromServer(bno, page).then(result => {
        console.log(result);
        const ul = document.getElementById('cmtListArea');
        if (result.cmtList.length > 0) {
            ul.innerHTML = ""; // 기존 댓글 제거
            ul.style.display = "block"; // 댓글이 있을 때는 ul 보이기

            for (let cvo of result.cmtList) {
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
        } else {
            ul.style.display = "none"; // 댓글이 없을 때는 ul 숨기기
        }
    });
}

document.addEventListener('click', (e) => {
    const target = e.target;

    if (target.classList.contains('mod')) {
        let li = target.closest('li');
        let cmtText = li.querySelector('.mb-0').innerText;
        document.getElementById('cmtTextMod').value = cmtText;

        let cmtWriter = li.querySelector('.text-primary').innerText;
        document.getElementById('cmtWriterMod').innerHTML = `<b>${cmtWriter}</b> 님의 댓글 내용 수정`;

        let cno = li.dataset.cno;
        document.getElementById('cmtModBtn').setAttribute("data-cno", cno);
    }

    if (target.id == 'cmtModBtn') {
        let cmtData = {
            cno: target.dataset.cno,
            content: document.getElementById('cmtTextMod').value
        };
        updateCommentToServer(cmtData).then(result => {
            if (result == '1') {
                alert("댓글 수정 성공");
            } else {
                alert("댓글 수정 실패");
            }
            document.querySelector('.btn-close').click(); // 모달창 닫기
            spreadCommentList(bnoVal);
        });
    }

    if (target.classList.contains('del')) {
        let li = target.closest('li');
        let cno = li.dataset.cno;
        removeCommentToServer(cno).then(result => {
            if (result == '1') {
                alert("댓글 삭제 성공");
            } else {
                alert("댓글 삭제 실패");
            }
            spreadCommentList(bnoVal);
        });
    }

    if (target.id == 'moreBtn') {
        let page = target.dataset.page;
        spreadCommentList(bnoVal, page);
    }
});

async function postCommentToServer(cmtData) {
    try {
        const url = "/comment/post";
        const config = {
            method: "post",
            headers: {
                'Content-Type': 'application/json; charset=utf-8'
            },
            body: JSON.stringify(cmtData)
        };
        const resp = await fetch(url, config);
        const result = await resp.text(); // isOk
        return result;
    } catch (error) {
        console.log(error);
    }
}

async function getCommentListFromServer(bno, page) {
    try {
        const resp = await fetch(`/comment/${bno}/${page}`);
        const result = await resp.json();
        return result;
    } catch (error) {
        console.log(error);
    }
}

async function updateCommentToServer(cmtData) {
    try {
        const url = "/comment/modify";
        const config = {
            method: 'put',
            headers: {
                'Content-Type': 'application/json; charset=utf-8'
            },
            body: JSON.stringify(cmtData)
        };
        const resp = await fetch(url, config);
        const result = await resp.text(); // isOk
        return result;
    } catch (error) {
        console.log(error);
    }
}

async function removeCommentToServer(cno) {
    try {
        const url = `/comment/${cno}`;
        const config = { method: 'delete' };
        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
}