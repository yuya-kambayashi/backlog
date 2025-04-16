function renderTicketChart(ticketData, containerId) {
    const ctx = document.getElementById(containerId).getContext("2d");

    // ticketData は HTML 側で <script>タグから定義されている想定
    const input = JSON.parse(ticketData.toString());
    console.log(input);
    const labels = Object.keys(input); // ['Alice', 'Bob']
    console.log(labels);
    console.log(input['Alice']['To Do']);

    const states = ['未対応', '処理中', '処理済み', '完了'];

    const datasets = states.map((state, i) => {
        const color = ['#ED8077', '#4488C5', '#5EB5A6', '#A1AF2F'][i]; // 状態別カラー
        return {
            label: state,
            data: labels.map(user => input[user][state] || 0),
            backgroundColor: color
        };
    });

    console.log(datasets);

    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: datasets
        },
        options: {
            indexAxis: 'y',
            responsive: true,
            // plugins: {
            //     title: {
            //         display: true,
            //         text: 'チケットの状態（担当者ごと）'
            //     }
            // },
            scales: {
                x: {
                    stacked: true
                },
                y: {
                    stacked: true,
                    beginAtZero: true
                }
            }
        }
    });
}