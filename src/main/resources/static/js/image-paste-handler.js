function setupImagePasteHandler() {
    document.getElementById("comment-input-area").addEventListener("paste", async function (event) {
        let items = (event.clipboardData || event.originalEvent.clipboardData).items;

        for (let item of items) {
            if (item.kind === "file") {
                let file = item.getAsFile();
                let formData = new FormData();
                formData.append("file", file, "pasted.png");

                // CSRF トークン取得
                let csrfToken = document.querySelector('meta[name="_csrf"]').content;
                let csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

                try {
                    let response = await fetch("/upload2S3", {
                        method: "POST",
                        body: formData,
                        headers: {
                            [csrfHeader]: csrfToken // CSRF トークンをリクエストヘッダーに追加
                        }
                    });

                    if (!response.ok) {
                        throw new Error("ファイルアップロードに失敗しました");
                    }

                    let jsonResponse = await response.json();
                    console.log("アップロード成功:", jsonResponse);

                    // ファイルパスを取得
                    let fileUrl = jsonResponse.fileUrl;
                    console.log("保存先:", fileUrl);

                    // HTML に表示する場合
                    //document.getElementById("upload-area").value = "アップロードされたファイル: " + fileUrl;
                    //  document.getElementById("paste-area").innerHTML = jsonResponse.pathWithTag;
                    console.log(document.getElementById("comment-input-area").value);
                    document.getElementById("comment-input-area").value += "\n";
                    document.getElementById("comment-input-area").value += jsonResponse.pathWithTag;
                } catch (error) {
                    console.error("エラー:", error);
                }
            }
        }
    });
}

document.addEventListener("DOMContentLoaded", setupImagePasteHandler);
