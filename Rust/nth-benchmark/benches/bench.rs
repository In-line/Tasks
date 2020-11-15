use criterion::{criterion_group, criterion_main, Criterion, Fun, Throughput, BenchmarkId};

pub fn prime(n: u32) -> bool {
    (2..((n as f32).sqrt() as u32) + 1).all(|i| n % i != 0)
}

pub fn nth_alik(n: u32) -> u32 {
    (2..).filter(|n| prime(*n)).take(n as usize).last().unwrap_or(1)
}

pub fn nth_gev(n: u32) -> u32 {
    let mut cur = 1;

    for _ in 0..n + 1 {
        cur += 1;
        while !prime(cur) {
            cur += 1;
        }
    }

    cur
}



fn nth_bench(c: &mut Criterion) {

    let mut group = c.benchmark_group("nth");
    for i in 0..1u32 {
        group.throughput(Throughput::Elements((i + 1) as u64));
        group.bench_with_input(BenchmarkId::new("nth_gev", i), &i, |b, &i| {
            b.iter(||nth_gev(i));
        });

        group.bench_with_input(BenchmarkId::new("nth_alik", i), &i, |b, &i| {
            b.iter(||nth_gev(i));
        });
    }
    group.finish();
}

Q
criterion_group!(nth, nth_bench);
criterion_main! {
    nth
}
