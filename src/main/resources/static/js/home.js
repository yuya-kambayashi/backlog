const ctx = document.getElementById('ticketStatusChart').getContext('2d');

new Chart(ctx, {
    type: 'bar',
    data: {
        labels: ['Alice', 'Bob'],
        datasets: [
            {
                label: '未対応',
                data: [3, 2],
                backgroundColor: '#ED8077'
            },
            {
                label: '処理中',
                data: [2, 4],
                backgroundColor: '#4488C5'
            },
            {
                label: '処理済み',
                data: [5, 1],
                backgroundColor: '#5EB5A6'
            },
            {
                label: '完了',
                data: [2, 4],
                backgroundColor: '#A1AF2F'
            }
        ]
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
                //beginAtZero: true,
                // title: {
                //     display: true,
                //     text: 'チケット数'
                // }
            }
        }
    }
});