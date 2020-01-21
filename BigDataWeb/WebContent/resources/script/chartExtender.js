
function skinChart() {
            this.cfg.shadow = false;
            this.cfg.title = '';
            this.cfg.seriesColors = ['#03A9F4', '#E91E63', '#4CAF50', '#FFC107'];
            this.showTooltip = true;
            this.cfg.grid = {
                background: '#ffffff',
                borderColor: '#ffffff',
                gridLineColor: '#F5F5F5',
                shadow: false
            };
            this.cfg.legend= {
                    show: true,
                    location: 'n',
                    placement: 'outsideGrid'
                };
            this.cfg.axesDefaults = {
                borderWidth: 0.1,
                borderColor: 'bdbdbd',
                rendererOptions: {
                    textColor: '#666F77'
                }
            };
            this.cfg.seriesDefaults = {
                shadow: false,
                lineWidth: 1,
                markerOptions: {
                    shadow: false,
                    size: 7,
                    style: 'circle'
                }
            }
        }

        function skinBar() {
            this.cfg.shadow = false;
            this.cfg.title = '';
            this.cfg.seriesColors = ['#4CAF50', '#FFC107'];
            this.cfg.grid = {
                background: '#ffffff',
                borderColor: '#ffffff',
                gridLineColor: '#F5F5F5',
                shadow: false
            };
            this.cfg.axesDefaults = {
                borderWidth: 0.1,
                borderColor: 'bdbdbd',
                rendererOptions: {
                    textColor: '#666F77'
                }
            };
            this.cfg.seriesDefaults = {
                shadow: false,
                lineWidth: 1,
                renderer: $.jqplot.BarRenderer,
                markerOptions: {
                    shadow: false,
                    size: 7,
                    style: 'circle'
                }
            }
        }

        function skinArea() {
            this.cfg.shadow = false;
            this.cfg.title = '';
            this.cfg.seriesColors = ['#FFC107', '#4CAF50'];
            this.cfg.grid = {
                background: '#ffffff',
                borderColor: '#ffffff',
                gridLineColor: '#F5F5F5',
                shadow: false
            };
            this.cfg.axesDefaults = {
                borderWidth: 0.1,
                borderColor: 'bdbdbd',
                rendererOptions: {
                    textColor: '#666F77'
                }
            };
            this.cfg.seriesDefaults = {
                shadow: false,
                lineWidth: 1,
                markerOptions: {
                    shadow: false,
                    size: 7,
                    style: 'circle'
                }
            }
        }

        function skinBubble() {
            this.cfg.shadow = false;
            this.cfg.title = '';
            this.cfg.seriesColors = ['#4CAF50', '#FFC107'];
            this.cfg.grid = {
                background: '#ffffff',
                borderColor: '#ffffff',
                gridLineColor: '#F5F5F5',
                shadow: false
            };
            this.cfg.axesDefaults = {
                rendererOptions: {
                    textColor: '#666F77'
                }
            };
            this.cfg.seriesDefaults = {
                shadow: false,
                lineWidth: 1,
                renderer: $.jqplot.BubbleRenderer,
                markerOptions: {
                    shadow: false,
                    size: 7,
                    style: 'circle'
                }
            }
        }

        function skinZoom() {
            this.cfg.shadow = false;
            this.cfg.title = '';
            this.cfg.seriesColors = ['#4CAF50', '#FFC107'];
            this.cfg.grid = {
                background: '#ffffff',
                borderColor: '#ffffff',
                gridLineColor: '#F5F5F5',
                shadow: false
            };
            this.cfg.axesDefaults = {
                rendererOptions: {
                    textColor: '#666F77'
                }
            };
            this.cfg.seriesDefaults = {
                shadow: false,
                lineWidth: 1,
                markerOptions: {
                    shadow: false,
                    size: 7,
                    style: 'circle'
                }
            }
        }

        function skinPie() {
            this.cfg.shadow = false;
            this.cfg.title = '';
            this.cfg.seriesColors = ['#4CAF50', '#03A9F4', '#673AB7', '#00ABC0'];
            this.cfg.grid = {
                background: '#ffffff',
                borderColor: '#ffffff',
                gridLineColor: '#F5F5F5',
                shadow: false
            };
            this.cfg.axesDefaults = {
                rendererOptions: {
                    textColor: '#666F77',
                }
            };
            this.cfg.seriesDefaults = {
                renderer: $.jqplot.PieRenderer,
                shadow: false,
                lineWidth: 1,
                markerOptions: {
                    shadow: false,
                    size: 7,
                    style: 'circle'
                }
            }
        }

        function skinDonut() {
            this.cfg.shadow = false;
            this.cfg.title = '';
            this.cfg.seriesColors = ['#03A9F4', '#E91E63', '#4CAF50', '#FFC107'];
            this.cfg.grid = {
                background: '#ffffff',
                borderColor: '#ffffff',
                gridLineColor: '#F5F5F5',
                shadow: false
            };
            this.cfg.axesDefaults = {
                rendererOptions: {
                    textColor: '#666F77',
                }
            };
            this.cfg.seriesDefaults = {
                renderer: $.jqplot.DonutRenderer,
                shadow: false,
                lineWidth: 1,
                markerOptions: {
                    shadow: false,
                    size: 7,
                    style: 'circle'
                }
            }
        }

        function skinBarAndLine() {
            this.cfg.shadow = false;
            this.cfg.title = '';
            this.cfg.seriesColors = ['#03A9F4', '#FFC107'];
            this.cfg.grid = {
                background: '#ffffff',
                borderColor: '#ffffff',
                gridLineColor: '#F5F5F5',
                shadow: false
            };
            this.cfg.axesDefaults = {
                borderWidth: 0.1,
                borderColor: 'bdbdbd',
                rendererOptions: {
                    textColor: '#666F77'
                }
            };
            this.cfg.seriesDefaults = {
                shadow: false,
                lineWidth: 5,
                renderer: $.jqplot.BarRenderer,
                markerOptions: {
                    shadow: false,
                    size: 7,
                    style: 'circle'
                }
            }
        }

        function skinMeterGauge() {
            this.cfg.title = '';
            this.cfg.seriesDefaults = {
                shadow: false,
                lineWidth: 5,
                renderer: $.jqplot.MeterGaugeRenderer,
                rendererOptions: {
                    shadow: false,
                    min: 100,
                    max: 800,
                    intervals: [200, 300, 400, 500, 600, 700, 800],
                    intervalColors: ['#03A9F4', '#E91E63', '#4CAF50', '#FFC107', '#03A9F4', '#E91E63', '#4CAF50']
                }
            }
        }

        function skinMultiAxis() {
            this.cfg.shadow = false;
            this.cfg.title = '';
            this.cfg.seriesColors = ['#4CAF50', '#FFC107'];
            this.cfg.grid = {
                background: '#ffffff',
                borderColor: '#ffffff',
                gridLineColor: '#F5F5F5',
                shadow: false
            };
            this.cfg.axesDefaults = {
                borderWidth: 0.1,
                borderColor: 'bdbdbd',
                rendererOptions: {
                    textColor: '#666F77'
                }
            };
            this.cfg.seriesDefaults = {
                shadow: false,
                lineWidth:1,
                renderer: $.jqplot.BarRenderer,
                markerOptions: {
                    shadow: false,
                    size: 7,
                    style: 'circle'
                }
            }
            
            
        }