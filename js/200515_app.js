var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var mysql = require('mysql');

var connection = mysql.createConnection({
    host: 'localhost',
    post: 3306,
    user: 'dbproj',
    password: 'dbproj1!!',
    database: 'SubwayHelperDB'
}); //db 연결

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

app.post('/getStationOfLine', function (req, res) {
    var line = req.body.line;
    console.log(line)
    var result = {
        stations:[],
    };
    module.exports = router;
    var router = express.Router();
    //connection.connect(); // createConnection 호출 시 connect 호출 불필요

    new Promise(function (res, rej) {
        
        connection.query('select station_info_stationName from station_line where line = ? order by station_info_stationName', [line], function (err, rows, fields) {
            if (!err) {
                if (rows.length === 0) {
                    message = '값이 없음';
                    console.log(message);
                } else {
                    message = '결과값 있음';
                    console.log(message);
                    result.line = rows;
                    res(result);
                }
            } else {
                rej(err);
            }
        });
    }).then(function (result) {
        res.json(result);
        console.log(result);
        //connection.end();
    }).catch(function (err) {
        console.log('query error : ' + err);
        message = '쿼리 에러';
        console.log(message)
        //connection.end();
    });
});


app.post('/getLine', function (req, res) {
    var station = req.body.station;
    console.log(station)
    var result = {
        line: [],
    };
    module.exports = router;
    var router = express.Router();
    //connection.connect(); // createConnection 호출 시 connect 호출 불필요

    new Promise(function (res, rej) {
        connection.query('select DISTINCT line from station_line ORDER BY line', function (err, rows, fields) {
            if (!err) {
                if (rows.length === 0) {
                    message = '값이 없음';
                    console.log(message);
                } else {
                    message = '결과값 있음';
                    console.log(message);
                    result.line = rows;
                    res(result);
                }
            } else {
                rej(err);
            }
        });
    }).then(function (result) {
        res.json(result);
        console.log(result);
        //connection.end();
    }).catch(function (err) {
        console.log('query error : ' + err);
        message = '쿼리 에러';
        console.log(message)
        //connection.end();
    });
});

app.post('/subway', function (req, res) {
    var station = req.body.station;
    console.log(station)
    var result = {
        line: [],
    };
    module.exports = router;
    var router = express.Router();
    //connection.connect(); // createConnection 호출 시 connect 호출 불필요

    new Promise(function (res, rej) {
        connection.query(`select * from station_info where stationName=?`, [station], function (err, rows, fields) {
            if (!err) {
                if (rows.length === 0) {
                    message = '값이 없음';
                    console.log(message);
                } else {
                    message = '결과값 있음';
                    console.log(message);

                    result.exitType = rows[0].exitType;
                    result.transfer = rows[0].transfer;

                
                    connection.query('select floor, position from toilet where station_info_stationName=?', [station], function (err, rows, fields) {
                        if (err) {
                            console.log(err);
                        } else {
                            result.toilet = rows;
                        }
                    });

                
                    connection.query('select floor, storeType_storeType from store where station_info_stationName=?', [station], function (err, rows, fields) {
                        if (err) {
                          console.log(err);
                        } else {
                          result.store = rows;
                        }
                    });
                    

                
                    connection.query('select floor, machineID, type from vanding where station_info_stationName=?', [station], function (err, rows, fields) {
                        if (err) {
                            console.log(err);
                        } else {
                            result.vanding = rows;
                        }
                    });
                    

                    connection.query('select line from station_line where station_info_stationName=?', [station], function (err, rows, fields) {
                        if (err) {
                            console.log(err);
                        } else {
                           result.line = rows;
                        }
                        res(result);
                    });
                }
            } else {
                rej(err);
            }
        });
    }).then(function (result) {
        res.json(result);
        console.log(result);
        //connection.end();
    }).catch(function (err) {
        console.log('query error : ' + err);
        message = '쿼리 에러';
        console.log(message)
        //connection.end();
    });
});


// 사용할 포트 번호
var port = 2020;
app.listen(port, function () {
    console.log('server on! 127.0.0.1');
    console.log('port: '+ port);
});
