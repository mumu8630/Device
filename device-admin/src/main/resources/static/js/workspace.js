//表格
const tabs = document.querySelectorAll('.tab');
const contents = document.querySelectorAll('.content');
tabs.forEach(tab => {
    tab.addEventListener('click', () => {
        const tabId = tab.getAttribute('data-tab');

        tabs.forEach(t => t.classList.remove('active'));
        tab.classList.add('active');

        contents.forEach(content => {
            if (content.getAttribute('id') === tabId) {
                content.classList.add('active');
            } else {
                content.classList.remove('active');
            }
        });
    });
});
//tab1图表
var firstEcharts = echarts.init(document.getElementById('first-chart'));
var secondEcharts = echarts.init(document.getElementById('second-chart'));
var thirdEcharts = echarts.init(document.getElementById('third-chart'));
// 定义两个获取数据的 Promise 函数
function fetchFirstChartData() {
    return fetch('/api/workspace/hotBorrow')
        .then(response => response.json())
        .catch(error => {
            console.error('Error fetching data for first chart:', error);
            return []; // 返回空数组以防图表渲染失败
        });
}
function fetchSecondChartData() {
    return fetch('/api/workspace/hotIdle')
        .then(response => response.json())
        .catch(error => {
            console.error('Error fetching data for second chart:', error);
            return []; // 返回空数组以防图表渲染失败
        });
}
function fetchThirdChartData() {
    return fetch('/api/workspace/barChart')
        .then(response => response.json())
        .catch(error => {
            console.error('Error fetching data for second chart:', error);
            return []; // 返回空数组以防图表渲染失败
        });
}

// 使用 Promise.all 同时获取两个图表的数据
Promise.all([fetchFirstChartData(), fetchSecondChartData(),fetchThirdChartData()])
    .then(([firstData, secondData, thirdData]) => {
        // 渲染第一个图表
        renderFirstChart(firstData);
        // 渲染第二个图表
        renderSecondChart(secondData);
        //渲染第三个图表
        renderThirdChart(thirdData);
    });

// 渲染第一个图表
function renderFirstChart(data) {
    // 使用获取到的数据设置 ECharts 配置项
    var option = {
        tooltip: {
            trigger: 'item'
        },

        legend: {
            left: '180',  // 将 legend 放在中间
            top: '30%',       // 距离顶部 5%
            orient: 'vertical',  // 垂直排列
            align: 'left',   // legend 在容器内左对齐
            itemGap: 3  ,   // 图例每项之间的间距
            itemWidth: 10, // 设置图例标记的宽度
            itemHeight: 10, // 设置图例标记的高度
            textStyle: {
                fontSize: 11
            }
        },
        series: [
            {
                name: '热门设备借用情况分析 ',
                type: 'pie',
                radius: ['50%', '70%'],
                center: ['25%', '50%'],
                avoidLabelOverlap: true,
                itemStyle: {
                    borderRadius: 4,
                    borderColor: '#fff',
                    borderWidth: 1
                },
                label: {
                    show: false,
                    position: 'center'
                },
                emphasis: {
                    label: {
                        show: true,
                        fontSize: 12,
                        fontWeight: 'bold'
                    }
                },
                labelLine: {
                    show: false
                },
                data: data,
            }
        ]
    };

    if (option && typeof option === 'object') {
        firstEcharts.setOption(option);
    };
    firstEcharts.setOption({
        // 设置不允许图表自动调整大小
        resize: false
    });

    window.addEventListener('resize', firstEcharts.resize);
}
// 渲染第二个图表
function renderSecondChart(data) {
    // 使用获取到的数据设置 ECharts 配置项
    var option = {
        tooltip: {
            trigger: 'item'
        },

        legend: {
            left: '180',  // 将 legend 放在中间
            top: '30%',       // 距离顶部 5%
            orient: 'vertical',  // 垂直排列
            align: 'left',   // legend 在容器内左对齐
            itemGap: 3  ,   // 图例每项之间的间距
            itemWidth: 10, // 设置图例标记的宽度
            itemHeight: 10, // 设置图例标记的高度
            textStyle: {
                fontSize: 11
            }
        },
        series: [
            {
                name: '热门设备借用情况分析 ',
                type: 'pie',
                radius: ['50%', '70%'],
                center: ['25%', '50%'],
                avoidLabelOverlap: true,
                itemStyle: {
                    borderRadius: 4,
                    borderColor: '#fff',
                    borderWidth: 1
                },
                label: {
                    show: false,
                    position: 'center'
                },
                emphasis: {
                    label: {
                        show: true,
                        fontSize: 12,
                        fontWeight: 'bold'
                    }
                },
                labelLine: {
                    show: false
                },
                data: data,
            }
        ]
    };

    if (option && typeof option === 'object') {
        secondEcharts.setOption(option);
    }
    secondEcharts.setOption({
        // 设置不允许图表自动调整大小
        resize: false
    });
    window.addEventListener('resize', secondEcharts.resize);

}
//渲染第三个图表
function renderThirdChart(data) {
    // 转换数据格式
    function transformData(data) {
        const transformed = [];
        for (const [product, values] of Object.entries(data)) {
            transformed.push({
                product,
                借用: values.借用,
                维护: values.维护,
                闲置: values.闲置
            });
        }
        return {
            dimensions: ['product', '借用', '维护', '闲置'],
            source: transformed
        };
    }
    const datasetForECharts = transformData(data);
    // 使用获取到的数据设置 ECharts 配置项
    var option;
    option = {
        color: ['#52bce887', '#13c8cc47', '#9bdc9ef5'], // 这里设置了整个图表的颜色数组
        legend: {},
        tooltip: {},
        dataset: datasetForECharts,
        xAxis: {type: 'category'},
        yAxis: {},
        // Declare several bar series, each will be mapped
        // to a column of dataset.source by default.
        series: [{type: 'bar'}, {type: 'bar'}, {type: 'bar'}]
    };
    if (option && typeof option === 'object') {
        thirdEcharts.setOption(option);
    }
    window.addEventListener('resize', thirdEcharts.resize);
}

//最近借用
fetch('/api/workspace/recentBorrow')
    .then(response => response.json())
    .then(data => {
        var tableBody = document.getElementById('tableBody');
        data.forEach(function(item,index) {
            // 解析日期字符串
            var borrowDate = new Date(item.borrowDate);
            // 获取年月日部分
            var formattedDate = borrowDate.getFullYear() + '-' + (borrowDate.getMonth() + 1) + '-' + borrowDate.getDate();
            var row = document.createElement('tr');
            row.innerHTML = '<td>' + (index + 1) + '</td>' +
                '<td>' + item.equipmentName + '</td>' +
                '<td>' + item.borrowUser + '</td>' +
                '<td>' + formattedDate + '</td>' +
                '<td style="color: ' +
                (item.borrowStatus === '未归还' ? '#fab42e' :
                    (item.borrowStatus === '已逾期' ? '#f80909b3'
                        : '#31a314')) +
                '">' + item.borrowStatus + '</td>';
            row.className = index % 2 === 0 ? 'even-row' : 'odd-row';
            tableBody.appendChild(row);
        });
    })
    .catch(error => {
        console.error('Error fetching data:', error);
    });

//tab2图表
var deadLineEchart = echarts.init(document.getElementById('deadLineChart'));
var borrowTimesEchart = echarts.init(document.getElementById('borrow-times-chart'));

// 使用 AJAX 从服务器获取数据
function fetchDeadLineChartData() {
    return fetch('/api/workspace/deadLine')
        .then(response => response.json())
        .catch(error => {
            console.error('Error fetching data for first chart:', error);
            return []; // 返回空数组以防图表渲染失败
        });
}
function fetchBorrowTimesChartData() {
    return fetch('/api/workspace/borrowTimes')
        .then(response => response.json())
        .catch(error => {
            console.error('Error fetching data for first chart:', error);
            return []; // 返回空数组以防图表渲染失败
        });
}

Promise.all([fetchDeadLineChartData(),fetchBorrowTimesChartData()])
    .then(([deadLineData,borrowTimesData]) => {
        renderDeadLineChart(deadLineData);
        renderBorrowTimesChart(borrowTimesData);
    });

function renderDeadLineChart(data) {
    var countdownMinutes = data.minutes; // 后端数据中包含分钟数
    var countdownSeconds = countdownMinutes * 60;
    var maxSecondsValue = 86400;
    var option = {
        series: [
            {
                type: 'gauge',
                // 调整仪表盘的外观
                radius: '100%',
                axisLine: {
                    lineStyle: {
                        width: 20, // 调整轴线的宽度
                        color: [
                            [0.3, '#fd666d'],
                            [0.7, 'rgba(238,160,86,0.87)'],
                            [1, '#67e0e3']
                        ]
                    }
                },
                // 调整指针样式
                pointer: {
                    itemStyle: {
                        color: 'auto',
                        width: 4, // 调整指针的宽度
                        length: '60%' // 调整指针的长度
                    },
                },
                // 调整刻度线的样式和位置
                axisTick: {
                    distance: -20, // 调整刻度线与轴线的距离
                    length: 6, // 调整刻度线的长度
                    lineStyle: {
                        color: '#fff', // 调整刻度线的颜色
                        width: 2 // 调整刻度线的宽度
                    }
                },
                // 调整分隔线的样式和位置
                splitLine: {
                    distance: -20, // 调整分隔线与轴线的距离
                    length: 20, // 调整分隔线的长度
                    lineStyle: {
                        color: '#fff', // 调整分隔线的颜色
                        width: 4 // 调整分隔线的宽度
                    }
                },
                // 调整轴标签的样式
                axisLabel: {
                    color: 'inherit',
                    distance: 30, // 调整轴标签与轴线的距离
                    fontSize: 7, // 调整轴标签的字体大小
                    formatter: function (maxSecondsValue) {
                        // 将秒数转换为分钟和秒的格式
                        var hours = Math.floor(maxSecondsValue / 3600);
                        var minutes = Math.floor((maxSecondsValue % 3600) / 60); // 计算剩余分钟数
                        return hours + 'h ' + minutes + 'min';
                    }
                },
                // 调整详情的样式
                detail: {
                    fontSize: 12, // 调整详情文字的字体大小

                    formatter: function (value) {
                        // 将剩余秒数转换为小时、分钟和秒的格式
                        var hours = Math.floor(value / 3600);
                        var minutes = Math.floor((value % 3600) / 60);
                        var seconds = value % 60;
                        return hours + 'h ' + minutes + 'min ' + seconds + 's';
                    },
                    color: 'inherit'
                },
                max: maxSecondsValue, // 设置仪表盘的最大值为24小时的秒数
                min: 0,
                // 初始化数据
                data: [
                    {
                        value: countdownSeconds
                    }
                ]
            }
        ]
    };
    var timer = setInterval(function () {
        countdownSeconds -= 1;
        deadLineEchart.setOption({
            series: [
                {
                    data: [
                        {
                            value: countdownSeconds
                        }
                    ]
                }
            ]
        });
        if (countdownSeconds <= 0) {
            clearInterval(timer);
        }
    }, 1000);
    if (option && typeof option === 'object') {
        deadLineEchart.setOption(option);
    }
    deadLineEchart.setOption({
        // 设置不允许图表自动调整大小
        resize: false
    });
    window.addEventListener('resize', deadLineEchart.resize);
}
function renderBorrowTimesChart(data){
    // 提取借阅日期作为横坐标
    var dates = data.map(function(item) {
        return item.borrowDate;
    });
    // 提取借阅数量作为系列数据
    var counts = data.map(function(item) {
        return item.borrowCount;
    });
    var option = {
        title:{
            text:'近期借用次数统计',
            left:'8%',
            top:'3%',
            textStyle:{
                color:'#333',
                fontSize: 15, // 标题字体大小
                fontWeight:'normal' // 标题字体粗细
            }
        },
        xAxis: {
            type: 'category',
            data: dates
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                data: counts,
                type: 'line',
                smooth: true
            }
        ]
    };
    if (option && typeof option === 'object') {
        borrowTimesEchart.setOption(option);
    }
    borrowTimesEchart.setOption({
        // 设置不允许图表自动调整大小
        resize: false
    });
    window.addEventListener('resize', borrowTimesEchart.resize);
}
//最近损坏
fetch('/api/workspace/recentBorrow')
    .then(response => response.json())
    .then(data => {
        var tableBody = document.getElementById('mainTableBody');
        data.forEach(function(item,index) {
            // 解析日期字符串
            var borrowDate = new Date(item.borrowDate);
            // 获取年月日部分
            var formattedDate = borrowDate.getFullYear() + '-' + (borrowDate.getMonth() + 1) + '-' + borrowDate.getDate();
            var row = document.createElement('tr');
            row.innerHTML = '<td>' + (index + 1) + '</td>' +
                '<td>' + item.equipmentName + '</td>' +
                '<td>' + item.borrowUser + '</td>' +
                '<td>' + formattedDate + '</td>' +
                '<td style="color: ' +
                (item.borrowStatus === '未归还' ? '#fab42e' :
                    (item.borrowStatus === '已逾期' ? '#f80909b3'
                        : '#31a314')) +
                '">' + item.borrowStatus + '</td>';
            row.className = index % 2 === 0 ? 'even-row' : 'odd-row';
            tableBody.appendChild(row);
        });
    })
    .catch(error => {
        console.error('Error fetching data:', error);
    });

