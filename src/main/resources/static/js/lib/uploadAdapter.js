class MyUploadAdapter {
    constructor( loader ) {
        // 파일로더 인스턴스 주입
        this.loader = loader;
    }

    // 업로드 프로세스 시작
    upload() {
        return this.loader.file
            .then( file => new Promise( ( resolve, reject ) => {
                this._initRequest();
                this._initListeners( resolve, reject, file );
                this._sendRequest( file );
            } ) );
    }

    // 업로드 중단
    abort() {
        if ( this.xhr ) {
            this.xhr.abort();
        }
    }

    // 전달된 URL로 XMLHttpRequest 객체 초기화
    _initRequest() {
        const xhr = this.xhr = new XMLHttpRequest();

        // return 데이터는 JSON, 요청은 POST로 하지만 사용자 커스터마이징 가능.
        // url에 controller 맵핑하면 됨.
        xhr.open( 'POST', '/post/newPost/image', true );
        xhr.responseType = 'json';
    }

    // XMLHttpRequest 리스너 초기화
    _initListeners( resolve, reject, file ) {
        const xhr = this.xhr;
        const loader = this.loader;
        const genericErrorText = `파일 업로드 실패!!, ${ file.name }.`;

        xhr.addEventListener( 'error', () => reject( genericErrorText ) );
        xhr.addEventListener( 'abort', () => reject() );
        xhr.addEventListener( 'load', () => {
            const response = xhr.response;

            // 업로드 오류 처리
            // 응답시 에러 + 메시지가 함께 왔을 때 메시지를 promise의 파라미터로 넘길 수 있음.
            if ( !response || response.error ) {
                console.log(response.error);
                return reject( response && response.error ? response.error.message : genericErrorText );
            }

            // 업로드 성공시 default에 URL을 담아 resolve
            // URL은 업로드된 이미지이며 게시글에 표시하기위해 사용됨.(파일 주소)
            resolve( {
                default: response.url
            } );
        } );

        // 이미지 업로드 현황 표시
        if ( xhr.upload ) {
            xhr.upload.addEventListener( 'progress', evt => {
                if ( evt.lengthComputable ) {
                    loader.uploadTotal = evt.total;
                    loader.uploaded = evt.loaded;
                }
            } );
        }
    }

    // 서버에 이미지 전송
    _sendRequest( file ) {
        // formData 생성
        const data = new FormData();

        data.append( 'upload', file );

        // 데이터를 넘길 때 헤더에 추가값을 넣을 수 있음.
        // 예를들어 xhr.setRequestHeader()로 요청 헤더에 토큰같은걸 넣어 인증 수행가능

        // 데이터 전송
        this.xhr.send( data );
    }
}

function MyCustomUploadAdapterPlugin( editor ) {
    editor.plugins.get( 'FileRepository' ).createUploadAdapter = ( loader ) => {
        // FileRepository가 사용할 에디터를 지정, 나머지 기능은 다 만들어져 있음.
        return new MyUploadAdapter( loader );
    };
}

ClassicEditor
    .create( document.querySelector( '#editor' ), {
        extraPlugins: [ MyCustomUploadAdapterPlugin ],
    } )
    .catch( error => {
        console.log( error );
    } );