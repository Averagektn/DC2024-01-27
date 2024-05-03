﻿using DC_Lab1.DTO.Interface;
using DC_Lab1.DTO;
using DC_Lab1.Services.Interfaces;
using Microsoft.EntityFrameworkCore;
using System.Globalization;
using AutoMapper;
using DC_Lab1.Models;
using Microsoft.Extensions.Hosting;
using DC_Lab1.DB.BaseDBContext;

namespace DC_Lab1.Services
{
    public class MarkerService(IMapper _mapper, BaseContext dbContext) : IMarkerService
    {
        public async Task<IResponseTo> CreateEnt(IRequestTo Dto)
        {
            var MarkerDto = (MarkerRequestTo)Dto;

            if (!Validate(MarkerDto))
            {
                throw new InvalidDataException("Incorrect data for CREATE Marker");

            }

            var Marker = _mapper.Map<Marker>(MarkerDto);
            dbContext.Markers.Add(Marker);
            await dbContext.SaveChangesAsync();
            var response = _mapper.Map<MarkerResponseTo>(Marker);
            return response;
        }

        public async Task DeleteEnt(int id)
        {
            try
            {
                var Marker = await dbContext.Markers.FindAsync(id);
                dbContext.Markers.Remove(Marker!);
                await dbContext.SaveChangesAsync();
                return;
            }
            catch
            {
                throw new Exception("Deletting Marker exception");
            }
        }

        public async Task<IResponseTo> GetEntById(int id)
        {

            var Marker = _mapper.Map<MarkerResponseTo>(await dbContext.Markers.FindAsync(id));
            return Marker is not null ? _mapper.Map<MarkerResponseTo>(Marker) : throw new ArgumentNullException($"Not found Marker: {id}");

        }

        public IEnumerable<IResponseTo> GetAllEnt()
        {
            try
            {
                return dbContext.Markers.Select(_mapper.Map<MarkerResponseTo>);

            }
            catch
            {
                throw new Exception("Getting all strickers exception");
            }
        }

        public async Task<IResponseTo> UpdateEnt(IRequestTo Dto)
        {
            var MarkerDto = (MarkerRequestTo)Dto;

            if (!Validate(MarkerDto))
            {
                throw new InvalidDataException("Incorrect data for UPDATE Marker");


            }
            var newMarker = _mapper.Map<Marker>(MarkerDto);
            dbContext.Markers.Update(newMarker);
            await dbContext.SaveChangesAsync();
            var Marker = _mapper.Map<MarkerResponseTo>(await dbContext.Markers.FindAsync(newMarker.Id));
            return Marker;


        }

        public bool Validate(MarkerRequestTo dto)
        {
            if (dto.name?.Length < 2 || dto.name?.Length > 32)
                return false;
            return true;
        }


    }
}
