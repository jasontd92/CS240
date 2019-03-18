#include <sys/types.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/socket.h>
#include <netdb.h>

/* Recommended max cache and object sizes */
#define MAX_CACHE_SIZE 1049000
#define MAX_OBJECT_SIZE 102400

/* You won't lose style points for including this long line in your code */
static const char *user_agent_hdr = "User-Agent: Mozilla/5.0 (X11; Linux x86_64; rv:10.0.3) Gecko/20120305 Firefox/10.0.3\r\n";

int helper(char buffer[1024]){
    printf("Recieved: %s\n",buffer );
    return 0;
}

int main(int argc, char *argv[])
{ 
    printf("%s\n", user_agent_hdr);
    //printf("Arguments: %d\n", argc);
    //printf("Argument 1: %s\n", argv[0]);
    //printf("Argument 2: %s\n", argv[1]);

    struct addrinfo hints, *listp, *p;
    //printf("A");
    int listenfd, optval=1;
    int valread;
    char buffer[1024] = {0}; 
    int LISTENQ = 100; //listening queue
	struct sockaddr_storage peer_addr;
	socklen_t peer_addr_len;
    char *port;
    //printf("Before strcpy");
    strcpy(port, argv[1]);
    printf("Port: %s\n", port);

    /* Get a list of potential server addresses */
    memset(&hints, 0, sizeof(struct addrinfo));
    hints.ai_socktype = SOCK_STREAM;             /* Accept connect. */
    hints.ai_flags = AI_PASSIVE | AI_ADDRCONFIG; /* …on any IP addr */
    hints.ai_flags |= AI_NUMERICSERV;            /* …using port no. */
    getaddrinfo(NULL, port, &hints, &listp);

    /* Walk the list for one that we can bind to */
    for (p = listp; p; p = p->ai_next) {
        //printf("In for loop\n");
        /* Create a socket descriptor */
        if ((listenfd = socket(p->ai_family, p->ai_socktype, 
                               p->ai_protocol)) < 0)
            continue;  /* Socket failed, try the next */

        /* Eliminates "Address already in use" error from bind */
        setsockopt(listenfd, SOL_SOCKET, SO_REUSEADDR, 
                   (const void *)&optval , sizeof(int));

        /* Bind the descriptor to the address */
        if (bind(listenfd, p->ai_addr, p->ai_addrlen) == 0)
            break; /* Success */
        close(listenfd); /* Bind failed, try the next */
    }
    //printf("Exited for loop\n");
    /* Clean up */
    freeaddrinfo(listp);
    if (!p){ /* No address worked */
        printf("No address worked\n");
        return -1;
    }

    /* Make it a listening socket ready to accept conn. requests */
    if (listen(listenfd, LISTENQ) < 0) {
        close(listenfd);
        printf("error\n");
        return -1;
    }
    //printf("just before accept() call\n");
    int new_sfd = accept(listenfd, (struct sockaddr *) &peer_addr, &peer_addr_len);
    //printf("new_sfd = %d\n", new_sfd);
    //printf("just before read() call\n");
    valread = read( new_sfd , buffer, 1024); 
    //printf("just before helper() call\n");
    helper(buffer);
    //return listenfd;
    return 0;
}

