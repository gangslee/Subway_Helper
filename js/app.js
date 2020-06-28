var express = require('express');
var app = express();
var mysql = require('mysql');
// 앱 DB연동을 위한 모듈 설정

var connection = mysql.createConnection({
    host: 'localhost',
    post: 3306,
    user: 'dbproj',
    password: 'dbproj1!!',
    database: 'SubwayHelperDB'
}); 
// 연동에 사용되는 DB접속 정보 설정


//앱에서 설정한 호선의 역사명들을 불러오는데 사용
app.get('/getStationOfLine', function (req, res) {
    console.log(req.query.line);
    var line = req.query.line;
    console.log(line)
    var result = {};
    
    //비동기 처리를 기다리기 위해 Promise 객체 사용
    new Promise(function (res, rej) {
        //앱에서 설정한 호선의 역사명들을 불러오고 이름순으로 정렬하는 쿼리 실행
        connection.query('select stationName from station where line = ? and stationInfo_stationID is NOT NULL order by stationName', [line], function (err, rows, fields) {
            if (!err) {
                if (rows.length === 0) {
                    message = '값이 없음';
                    console.log(message);
                } else {
                    message = '결과값 있음';
                    console.log(message);
                    result.stations = rows;
                    res(result);
                }
            } else {
                console.log(err);
                rej(err);
            }
        });
    }).then(function (result) {  // Promise 객체 내의 function이 잘 실행 될 경우 해당 부분 진행
        res.json(result);
        console.log(result);
    }).catch(function (err) {   // Promise 객체 내의 function에서 error가 발생하는 case를 catch
        console.log('query error : ' + err);
        message = '쿼리 에러';
        console.log(message)
    });
});


//station 테이블에 존재하는 모든 호선을 불러오는데 사용
app.get('/getLine', function (req, res) {
    var result = {};

    //비동기 처리를 기다리기 위해 Promise 객체 사용
    new Promise(function (res, rej) {
        //station 테이블에 존재하는 모든 호선을 불러오는 쿼리 실행
        connection.query('select DISTINCT line from station Where stationInfo_stationID is NOT NULL ORDER BY line', function (err, rows, fields) {
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
    }).then(function (result) { // Promise 객체 내의 function이 잘 실행 될 경우 해당 부분 진행
        res.json(result);
        console.log(result);
    }).catch(function (err) {   // Promise 객체 내의 function에서 error가 발생하는 case를 catch
        console.log('query error : ' + err);
        message = '쿼리 에러';
        console.log(message)
    });
});

//앱에서 사용자가 설정한 역사에 대한 정보를 조회하는데 사용
app.get('/subway', function (req, res) {
    var station = req.query.station;
    var line = req.query.line;
    line = line.replace("선","");
    line = line.replace("호","");
    console.log(station);
    console.log(line);
    var result = {};

    //앱에서 사용자가 설정한 역사에 대한 정보를 조회하는 쿼리들을 실행
    new Promise(function (res, rej) {
        // station 테이블과 stationinfo 테이블간에 inner join을 실시하여 원하는 호선의 역사의 주소, 전화번호를 가져오는 쿼리
        connection.query(`select address, tel from station inner join stationinfo where stationName=? and line=? and
        stationID = stationInfo_stationID`, [station, line], function (err, rows, fields) {
            if (!err) {
                if (rows.length === 0) {
                    message = '값이 없음';
                    console.log(message);
                } else { // 상단 쿼리 실행시 값이 존재할경우 다른 정보들을 추가적으로 가져옴
                    message = '결과값 있음';
                    console.log(message);

                    result.address = rows[0].address;
                    result.tel = rows[0].tel;

                    // stationtoilet 테이블과 toiletinfo 테이블간에 inner join을 실시하여 원하는 역사의 화장실 세부정보(위치정보들)들을 가져오는 쿼리
                    connection.query(`select floor, position from stationtoilet inner join toiletinfo where station_stationName=?
                    and toiletID = stationToilet_toiletID`, [station], function (err, rows, fields) {
                        if (err) {
                            console.log(err);
                        } else {
                            result.toilet = rows;
                        }
                    });

                    // stationstore 테이블과 storeinfo 테이블간에 inner join을 실시하여 원하는 역사의 편의점 세부정보(종류, 위치정보)들을 가져오는 쿼리
                    connection.query(`SELECT floor, storeType FROM stationstore inner join storeinfo where storeID = stationStore_storeID and station_stationName=?`, [station], function (err, rows, fields) {
                        if (err) {
                            console.log(err);
                        } else {
                            result.store = rows;
                        }
                    });
                    
                    // stationvanding 테이블과 vandinginfo 테이블간에 inner join을 실시하여 원하는 역사의 자판기 세부정보(종류, 위치정보)들을 가져오는 쿼리
                    connection.query('select floor, vandingType from stationvanding inner join vandinginfo where vandingID = stationVanding_vandingID and station_stationName=?', [station], function (err, rows, fields) {
                        if (err) {
                            console.log(err);
                        } else {
                            result.vanding = rows;
                        }
                    });
                    
                    // 설정한 역사의 모든 호선 정보를 가져오는 쿼리
                    connection.query('select line from station where stationName=?', [station], function (err, rows, fields) {
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
    }).then(function (result) { // Promise 객체 내의 function이 잘 실행 될 경우 해당 부분 진행
        res.json(result);
        console.log(result);
    }).catch(function (err) {   // Promise 객체 내의 function에서 error가 발생하는 case를 catch
        console.log('query error : ' + err);
        message = '쿼리 에러';
        console.log(message)
    });
});


// 사용할 포트 번호 설정 및 서버 구축
var port = 2020;
app.listen(port, function () {
    console.log('server on! 127.0.0.1');
    console.log('port: '+ port);
});
