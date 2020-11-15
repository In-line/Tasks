import PQueue from 'p-queue'

const globalQueue = new PQueue({interval: 16});

const fetchLimited = async (...args) => {
    return globalQueue.add(() => fetch(...args))
}

export default fetchLimited;