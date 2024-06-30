(function($) {
  'use strict';
  function formatDate(dateString) {
    var date = new Date(dateString);
    var options = { year: 'numeric', month: 'long', day: 'numeric' };
    return date.toLocaleDateString('en-US', options);
  }
  $(function() {
    if ($("#performanceLine").length) {
      $.ajax({
        url: "../admin/dash",
        type: "POST",
        data: {
          action: "income" // Replace 'actionName' with the desired action
        },
        success: function (data) {
          // Dữ liệu từ Servlet đã được trả về ở định dạng JSON
          // Bạn có thể sử dụng dữ liệu này để cấu hình biểu đồ
          var labels = data.estimatedIncome.map(function (item) {
            return formatDate(item.date);
          });
          var datasets = [{
            label: 'Paymentgit',
            data: data.estimatedIncome.map(function (item) {
              return item.amount;
            }),
            backgroundColor: saleGradientBg,
            borderColor: [
              '#1F3BB3',
            ],
            borderWidth: 1.5,
            fill: true, // 3: no fill
            pointBorderWidth: 1,
            pointRadius: [4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4],
            pointHoverRadius: [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2],
            pointBackgroundColor: ['#1F3BB3)', '#1F3BB3', '#1F3BB3', '#1F3BB3', '#1F3BB3)', '#1F3BB3', '#1F3BB3', '#1F3BB3', '#1F3BB3)', '#1F3BB3', '#1F3BB3', '#1F3BB3', '#1F3BB3)'],
            pointBorderColor: ['#fff', '#fff', '#fff', '#fff', '#fff', '#fff', '#fff', '#fff', '#fff', '#fff', '#fff', '#fff', '#fff',],
            // Các cấu hình khác của dataset
          }];
          const ctx = document.getElementById('performanceLine');
          var graphGradient = document.getElementById("performanceLine").getContext('2d');
          var graphGradient2 = document.getElementById("performanceLine").getContext('2d');
          var saleGradientBg = graphGradient.createLinearGradient(5, 0, 5, 100);
          saleGradientBg.addColorStop(0, 'rgba(26, 115, 232, 0.18)');
          saleGradientBg.addColorStop(1, 'rgba(26, 115, 232, 0.02)');
          var saleGradientBg2 = graphGradient2.createLinearGradient(100, 0, 50, 150);
          saleGradientBg2.addColorStop(0, 'rgba(0, 208, 255, 0.19)');
          saleGradientBg2.addColorStop(1, 'rgba(0, 208, 255, 0.03)');

          new Chart(ctx, {
            type: 'line',
            data: {
              labels: labels,
              datasets: datasets
            },
            options: {
              responsive: true,
              maintainAspectRatio: false,
              elements: {
                line: {
                  tension: 0.4,
                }
              },

              scales: {
                y: {
                  border: {
                    display: false
                  },
                  grid: {
                    display: true,
                    color: "#F0F0F0",
                    drawBorder: false,
                  },
                  ticks: {
                    beginAtZero: false,
                    autoSkip: true,
                    maxTicksLimit: 4,
                    color: "#6B778C",
                    font: {
                      size: 10,
                    }
                  }
                },
                x: {
                  border: {
                    display: false
                  },
                  grid: {
                    display: false,
                    drawBorder: false,
                  },
                  ticks: {
                    beginAtZero: false,
                    autoSkip: true,
                    maxTicksLimit: 7,
                    color: "#6B778C",
                    font: {
                      size: 10,
                    }
                  }
                }
              },
              plugins: {
                legend: {
                  display: false,
                }
              }
            },
            plugins: [{
              afterDatasetUpdate: function (chart, args, options) {
                const chartId = chart.canvas.id;
                var i;
                const legendId = `${chartId}-legend`;
                const ul = document.createElement('ul');
                for (i = 0; i < chart.data.datasets.length; i++) {
                  ul.innerHTML += `
                  <li>
                    <span style="background-color: ${chart.data.datasets[i].borderColor}"></span>
                    ${chart.data.datasets[i].label}
                  </li>
                `;
                }
                return document.getElementById(legendId).appendChild(ul);
              }
            }]
          });
        }
      })
    }

    if ($("#status-summary").length) {
      $.ajax({
        url: "../admin/dash",
        type: "POST",
        data: {
          action: "income" // Replace 'actionName' with the desired action
        },
        success: function (data) {
          // Dữ liệu từ Servlet đã được trả về ở định dạng JSON
          // Bạn có thể sử dụng dữ liệu này để cấu hình biểu đồ
          var labels = data.estimatedIncome.map(function (item) {
            return formatDate(item.date);
          });
          var datasets = [{
            label: 'Income',
            data: data.estimatedIncome.map(function (item) {
              return item.amount;
            }),
            backgroundColor: saleGradientBg,
            borderColor: [
              '#1F3BB3',
            ],
            borderWidth: 1.5,
            fill: true, // 3: no fill
            pointBorderWidth: 1,
            pointRadius: [4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4],
            pointHoverRadius: [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2],
            pointBackgroundColor: ['#1F3BB3)', '#1F3BB3', '#1F3BB3', '#1F3BB3', '#1F3BB3)', '#1F3BB3', '#1F3BB3', '#1F3BB3', '#1F3BB3)', '#1F3BB3', '#1F3BB3', '#1F3BB3', '#1F3BB3)'],
            pointBorderColor: ['#fff', '#fff', '#fff', '#fff', '#fff', '#fff', '#fff', '#fff', '#fff', '#fff', '#fff', '#fff', '#fff',],
            // Các cấu hình khác của dataset
          }];
      const statusSummaryChartCanvas = document.getElementById('status-summary');
      new Chart(statusSummaryChartCanvas, {
        type: 'line',
        data: {
          labels: ["SUN", "MON", "TUE", "WED", "THU", "FRI"],
          datasets: [{
              label: '# of Votes',
              data: [50, 68, 70, 10, 12, 80],
              backgroundColor: "#ffcc00",
              borderColor: [
                  '#01B6A0',
              ],
              borderWidth: 2,
              fill: false, // 3: no fill
              pointBorderWidth: 0,
              pointRadius: [0, 0, 0, 0, 0, 0],
              pointHoverRadius: [0, 0, 0, 0, 0, 0],
          }]
        },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          elements: {
            line: {
                tension: 0.4,
            }
        },
          scales: {
            y: {
              border: {
                display: false
              },
              display: false,
              grid: {
                display: false,
              },
            },
            x: {
              border: {
                display: false
              },
              display: false,
              grid: {
                display: false,
              }
            }
          },
          plugins: {
            legend: {
                display: false,
            }
          }
        }
      });
        }
      })
    }

    if ($("#marketingOverview").length) { 
      const marketingOverviewCanvas = document.getElementById('marketingOverview');
      new Chart(marketingOverviewCanvas, {
        type: 'bar',
        data: {
          labels: ["JAN","FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"],
          datasets: [{
            label: 'Last week',
            data: [110, 220, 200, 190, 220, 110, 210, 110, 205, 202, 201, 150],
            backgroundColor: "#52CDFF",
            borderColor: [
                '#52CDFF',
            ],
              borderWidth: 0,
              barPercentage: 0.35,
              fill: true, // 3: no fill
              
          },{
            label: 'This week',
            data: [215, 290, 210, 250, 290, 230, 290, 210, 280, 220, 190, 300],
            backgroundColor: "#1F3BB3",
            borderColor: [
                '#1F3BB3',
            ],
            borderWidth: 0,
              barPercentage: 0.35,
              fill: true, // 3: no fill
          }]
        },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          elements: {
            line: {
                tension: 0.4,
            }
        },
        
          scales: {
            y: {
              border: {
                display: false
              },
              grid: {
                display: true,
                drawTicks: false,
                color:"#F0F0F0",
                zeroLineColor: '#F0F0F0',
              },
              ticks: {
                beginAtZero: false,
                autoSkip: true,
                maxTicksLimit: 4,
                color:"#6B778C",
                font: {
                  size: 10,
                }
              }
            },
            x: {
              border: {
                display: false
              },
              stacked: true,
              grid: {
                display: false,
                drawTicks: false,
              },
              ticks: {
                beginAtZero: false,
                autoSkip: true,
                maxTicksLimit: 7,
                color:"#6B778C",
                font: {
                  size: 10,
                }
              }
            }
          },
          plugins: {
            legend: {
                display: false,
            }
          }
        },
        plugins: [{
          afterDatasetUpdate: function (chart, args, options) {
              const chartId = chart.canvas.id;
              var i;
              const legendId = `${chartId}-legend`;
              const ul = document.createElement('ul');
              for(i=0;i<chart.data.datasets.length; i++) {
                  ul.innerHTML += `
                  <li>
                    <span style="background-color: ${chart.data.datasets[i].borderColor}"></span>
                    ${chart.data.datasets[i].label}
                  </li>
                `;
              }
              return document.getElementById(legendId).appendChild(ul);
            }
        }]
      });
    }

    if ($('#totalVisitors').length) {
      var bar = new ProgressBar.Circle(totalVisitors, {
        color: '#fff',
        // This has to be the same size as the maximum width to
        // prevent clipping
        strokeWidth: 15,
        trailWidth: 15, 
        easing: 'easeInOut',
        duration: 1400,
        text: {
          autoStyleContainer: false
        },
        from: {
          color: '#52CDFF',
          width: 15
        },
        to: {
          color: '#677ae4',
          width: 15
        },
        // Set default step function for all animate calls
        step: function(state, circle) {
          circle.path.setAttribute('stroke', state.color);
          circle.path.setAttribute('stroke-width', state.width);
  
          var value = Math.round(circle.value() * 100);
          if (value === 0) {
            circle.setText('');
          } else {
            circle.setText(value);
          }
  
        }
      });
  
      bar.text.style.fontSize = '0rem';
      bar.animate(.64); // Number from 0.0 to 1.0
    }

    if ($('#visitperday').length) {
      var bar = new ProgressBar.Circle(visitperday, {
        color: '#fff',
        // This has to be the same size as the maximum width to
        // prevent clipping
        strokeWidth: 15,
        trailWidth: 15,
        easing: 'easeInOut',
        duration: 1400,
        text: {
          autoStyleContainer: false
        },
        from: {
          color: '#34B1AA',
          width: 15
        },
        to: {
          color: '#677ae4',
          width: 15
        },
        // Set default step function for all animate calls
        step: function(state, circle) {
          circle.path.setAttribute('stroke', state.color);
          circle.path.setAttribute('stroke-width', state.width);
  
          var value = Math.round(circle.value() * 100);
          if (value === 0) {
            circle.setText('');
          } else {
            circle.setText(value);
          }
  
        }
      });
  
      bar.text.style.fontSize = '0rem';
      bar.animate(.34); // Number from 0.0 to 1.0
    }

    if ($("#doughnutChart").length) {
      $.ajax({
        url: "../admin/dash",
        type: "POST",
        data: {
          action: "booking" // Replace 'actionName' with the desired action
        },
        success: function (data) {
          // Dữ liệu từ Servlet đã được trả về ở định dạng JSON
          // Bạn có thể sử dụng dữ liệu này để cấu hình biểu đồ
          const acp =data.bookingAccepted;
          const rej = data.bookingReject;


      const doughnutChartCanvas = document.getElementById('doughnutChart');
      new Chart(doughnutChartCanvas, {
        type: 'doughnut',
        data: {
          labels: ['Booking Accepted','Booking Reject'],
          datasets: [{
            data:[acp,rej],
            backgroundColor: [
              "#93DC5C",
              '#52CDFF'

            ],
            borderColor: [
              "#93DC5C",
              '#52CDFF',
            ],
          }]
        },
        options: {
          cutout: 90,
          animationEasing: "easeOutBounce",
          animateRotate: true,
          animateScale: false,
          responsive: true,
          maintainAspectRatio: true,
          showScale: true,
          legend: false,
          plugins: {
            legend: {
                display: false,
            }
          }
        },
        plugins: [{
          afterDatasetUpdate: function (chart, args, options) {
              const chartId = chart.canvas.id;
              var i;
              const legendId = `${chartId}-legend`;
              const ul = document.createElement('ul');
              for(i=0;i<chart.data.datasets[0].data.length; i++) {
                  ul.innerHTML += `
                  <li>
                    <span style="background-color: ${chart.data.datasets[0].backgroundColor[i]}"></span>
                    ${chart.data.labels[i]}
                  </li>
                `;
              }
              return document.getElementById(legendId).appendChild(ul);
            }
        }]
      });
        }
      })
    }
    if ($("#doughnutChartPurple").length) {
      $.ajax({
        url: "../admin/dash",
        type: "POST",
        data: {
          action: "account" // Replace 'actionName' with the desired action
        },
        success: function (data) {
          // Dữ liệu từ Servlet đã được trả về ở định dạng JSON
          // Bạn có thể sử dụng dữ liệu này để cấu hình biểu đồ
          const mentee =data.mentee;
          const mentor = data.mentor;
          const manager = data.manager;

          const doughnutChartCanvas = document.getElementById('doughnutChartPurple');
          new Chart(doughnutChartCanvas, {
            type: 'doughnut',
            data: {
              labels: ['Mentee : '+data.count_mentee,'Mentor : '+data.count_mentor,'Manager : '+data.count_manager],
              datasets: [{
                data:[mentee,mentor,manager],
                backgroundColor: [
                  "#01FFFF",
                  "#0E4D92",
                  "#FFC0CB",

                ],
                borderColor: [
                  "#01FFFF",
                  "#0E4D92",
                  "#FFC0CB",
                ],
              }]
            },
            options: {
              cutout: 90,
              animationEasing: "easeOutBounce",
              animateRotate: true,
              animateScale: false,
              responsive: true,
              maintainAspectRatio: true,
              showScale: true,
              legend: false,
              plugins: {
                legend: {
                  display: false,
                }
              }
            },
            plugins: [{
              afterDatasetUpdate: function (chart, args, options) {
                const chartId = chart.canvas.id;
                var i;
                const legendId = `${chartId}-legend`;
                const ul = document.createElement('ul');
                for(i=0;i<chart.data.datasets[0].data.length; i++) {
                  ul.innerHTML += `
                  <li>
                    <span style="background-color: ${chart.data.datasets[0].backgroundColor[i]}"></span>
                    ${chart.data.labels[i]}
                  </li>
                `;
                }
                return document.getElementById(legendId).appendChild(ul);
              }
            }]
          });
        }
      })
    }

    if ($("#leaveReport").length) {
      $.ajax({
        url: "../admin/dash",
        type: "POST",
        data: {
          action: "skill" // Replace 'actionName' with the desired action
        },
        success: function (data) {
          console.log(data);
          var skillKey = Object.keys(data.skill);
          var skillValue = Object.values(data.skill);
          // Dữ liệu từ Servlet đã được trả về ở định dạng JSON
          // Bạn có thể sử dụng dữ liệu này để cấu hình biểu đồ
          var labels = skillKey;
          var datasets = [{
            label: 'Skill',
            data: skillValue,
            backgroundColor: "#52CDFF",
            borderColor: [
              '#52CDFF',
            ],
            borderWidth: 0,
            fill: true, // 3: no fill
            barPercentage: 0.5,
          }];
      const leaveReportCanvas = document.getElementById('leaveReport');
      new Chart(leaveReportCanvas, {
        type: 'bar',
        data: {
          labels:labels,
          datasets: datasets,
        },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          elements: {
            line: {
                tension: 0.4,
            }
        },
          scales: {
            y: {
              border: {
                display: false
              },
              display: true,
              grid: {
                display: false,
                drawBorder: false,
                color:"rgba(255,255,255,.05)",
                zeroLineColor: "rgba(255,255,255,.05)",
              },
              ticks: {
                beginAtZero: true,
                autoSkip: true,
                maxTicksLimit: 5,
                fontSize: 10,
                color:"#6B778C",
                font: {
                  size: 10,
                }
              }
            },
            x: {
              border: {
                display: false
              },
              display: true,
              grid: {
                display: false,
              },
              ticks: {
                beginAtZero: false,
                autoSkip: true,
                maxTicksLimit: 7,
                fontSize: 10,
                color:"#6B778C",
                font: {
                  size: 10,
                }
              }
            }
          },
          plugins: {
            legend: {
                display: false,
            }
          }
        }
      });
        }
      })
    }


    if ($.cookie('staradmin2-pro-banner')!="true") {
      document.querySelector('#proBanner').classList.add('d-flex');
      document.querySelector('.navbar').classList.remove('fixed-top');
    }
    else {
      document.querySelector('#proBanner').classList.add('d-none');
      document.querySelector('.navbar').classList.add('fixed-top');
    }
    
    if ($( ".navbar" ).hasClass( "fixed-top" )) {
      document.querySelector('.page-body-wrapper').classList.remove('pt-0');
      document.querySelector('.navbar').classList.remove('pt-5');
    }
    else {
      document.querySelector('.page-body-wrapper').classList.add('pt-0');
      document.querySelector('.navbar').classList.add('pt-5');
      document.querySelector('.navbar').classList.add('mt-3');
      
    }
    document.querySelector('#bannerClose').addEventListener('click',function() {
      document.querySelector('#proBanner').classList.add('d-none');
      document.querySelector('#proBanner').classList.remove('d-flex');
      document.querySelector('.navbar').classList.remove('pt-5');
      document.querySelector('.navbar').classList.add('fixed-top');
      document.querySelector('.page-body-wrapper').classList.add('proBanner-padding-top');
      document.querySelector('.navbar').classList.remove('mt-3');
      var date = new Date();
      date.setTime(date.getTime() + 24 * 60 * 60 * 1000); 
      $.cookie('staradmin2-pro-banner', "true", { expires: date });
    });
    
  });
  // iconify.load('icons.svg').then(function() {
  //   iconify(document.querySelector('.my-cool.icon'));
  // });

  
})(jQuery);