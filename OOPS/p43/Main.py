import pyopencl as cl
import numpy as np


n = 1024


input_array = np.arange(n, dtype=np.float32)
output_array = np.empty_like(input_array)


platform = cl.get_platforms()[0]
device = platform.get_devices()[0]
context = cl.Context([device])
queue = cl.CommandQueue(context)


cl_input = cl.Buffer(
    context, cl.mem_flags.READ_ONLY | cl.mem_flags.COPY_HOST_PTR, hostbuf=input_array
)
cl_output = cl.Buffer(context, cl.mem_flags.WRITE_ONLY, output_array.nbytes)


program_source = """
    __kernel void square(__global const float* input, __global float* output) {
        int gid = get_global_id(0);
        output[gid] = input[gid] * input[gid];
    }
"""
program = cl.Program(context, program_source).build()


program.square(queue, input_array.shape, None, cl_input, cl_output)


cl.enqueue_barrier(queue)
cl.enqueue_copy(queue, output_array, cl_output)


for i in range(n):
    print(f"Input: {input_array[i]}, Output: {output_array[i]}")


cl_input.release()
cl_output.release()
