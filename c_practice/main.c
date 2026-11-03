#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>
#include <time.h>
#include <unistd.h>


#define BUFFER_SIZE = 7;
#define NUM_PRODUCERS = 2;
#define NUM_CONSUMERS = 2;
#define ITEMS_PER_THREAD = 10;

int buffer[BUFFER_SIZE];
int in = 0;
int out = 0;
int even_count = 0;

pthread_mutex_t mutex;
sem_t empty;
sem_t full;

void *producer(void *args) {
    unsigned int *seed = (unsigned int *) args;

    for(int i = 0; i < ITEMS_PER_THREAD; i++) {
        int item = rand_r(seed) % 100;

        sem_wait(&empty);
        pthread_mut4ex_lock(&mutex);
        buffer[in] = item;
        printf("Producer produced: %d\n", item);
        in = (in + 1) % BUFFER_SIZE;

        pthread_mutex_unlock(&mutex);

        sem_post(&full);
    }

    return NULL;
}

void *consumer(void *args) {
    for(int i = 0; i < ITEMS_PER_THREAD; i++) {
        sem_wait(&full);
        pthread_mutex_lock(&mutex);

        int value = buffer[out];
        printf("Consumer consumed: %d\n", value);
        buffer[out] = -1;

        if(value % 2 == 0) {
            even_count++;
        }

        out = (out + 1) % BUFFER_SIZE;

        pthread_mutex_unlock(&mutex);

        sem_post(&empty);
    }

    return NULL;
}

int main() {
    pthread_t producers[NUM_PRODUCERS];
    pthread_t consumers[NUM_CONSUMERS];

    unsigned int seeds[NUM_PRODUCERS];

    pthread_mutex_init(&mutex, NULL);
    
    sem_init(&empty, 0, BUFFER_SIZE);
    sem_init(&full, 0,0);

    for(int i = 0; i < NUM_PRODUCERS; i++) {
        seeds[i] = (unsigned int)time(NULL) + i;
        pthread_create(&producers[i], NULL, producer, &seeds[i]);
    }

    for(int i = 0; i < NUM_CONSUMERS; i++) {
        pthread_create(&consumers[i], NULL, consumer, &seeds[i]);
    }

    for(int i = 0; i< NUM_PRODUCERS; i++) {
        pthread_join(producers[i], NULL);
    }

    for(int i = 0; i < NUM_CONSUMERS; i++) {
        pthread_join(consumers[i], NULL);
    }

    printf("Total even nums consumed: %d\n", even_count);

    pthread_mutex_destroy(&mutex);
    sem_destroy(&empty);
    sem_destroy(&full);

    return 0;

}

