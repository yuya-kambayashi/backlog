function updateQueryAndSubmit(name) {
    console.log(name);

    let url = new URL(window.location.href);
    console.log(url);
    let params = new URLSearchParams(url.search);
    console.log(params);

    // チェックされた値を取得
    let checkedValues = [];
    document.querySelectorAll(`input[name="${name}"]:checked`).forEach((el) => {
        checkedValues.push(el.value);
    });
    console.log(checkedValues);


    if (checkedValues.length > 0) {
        params.set(name, checkedValues.join(",")); // カンマ区切りで格納
    } else {
        params.delete(name);
    }
    console.log(params);

    url.search = params.toString();
    console.log(url.toString());

    window.location.href = url.toString();
}
// ページ読み込み時にURLのパラメータを解析してチェック状態を復元
window.onload = function () {
    let url = new URL(window.location.href);
    let params = new URLSearchParams(url.search);

    document.querySelectorAll("input[type='checkbox']").forEach((el) => {
        let name = el.name;
        if (params.has(name)) {
            let values = params.get(name).split(","); // カンマ区切りを配列に
            if (values.includes(el.value)) {
                el.checked = true; // チェックを復元
            }
        }
    });
};